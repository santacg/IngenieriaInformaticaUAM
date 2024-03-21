--------------------------------------------------------------------------------
-- Procesador RISC V uniciclo curso Arquitectura Ordenadores 2023
-- Initial Release G.Sutter jun 2022. Last Rev. sep2023
-- 
--
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use work.RISCV_pack.all;

entity processorRV is
   port(
      Clk      : in  std_logic;                     -- Reloj activo en flanco subida
      Reset    : in  std_logic;                     -- Reset asincrono activo nivel alto
      -- Instruction memory
      IAddr    : out std_logic_vector(31 downto 0); -- Direccion Instr
      IDataIn  : in  std_logic_vector(31 downto 0); -- Instruccion leida
      -- Data memory
      DAddr    : out std_logic_vector(31 downto 0); -- Direccion
      DRdEn    : out std_logic;                     -- Habilitacion lectura
      DWrEn    : out std_logic;                     -- Habilitacion escritura
      DDataOut : out std_logic_vector(31 downto 0); -- Dato escrito
      DDataIn  : in  std_logic_vector(31 downto 0)  -- Dato leido
   );
end processorRV;

architecture rtl of processorRV is

  component alu_RV
    port (
      OpA     : in  std_logic_vector (31 downto 0); -- Operando A
      OpB     : in  std_logic_vector (31 downto 0); -- Operando B
      Control : in  std_logic_vector (3 downto 0);  -- Codigo de control=op. a ejecutar
      Result  : out std_logic_vector (31 downto 0); -- Resultado
      SignFlag: out std_logic;                      -- Sign Flag
      CarryOut: out std_logic;                      -- Carry bit
      ZFlag   : out std_logic                       -- Flag Z
    );
  end component;

  component reg_bank
     port (
        Clk   : in  std_logic;                      -- Reloj activo en flanco de subida
        Reset : in  std_logic;                      -- Reset asincrono a nivel alto
        A1    : in  std_logic_vector(4 downto 0);   -- Direccion para el primer registro fuente (rs1)
        Rd1   : out std_logic_vector(31 downto 0);  -- Dato del primer registro fuente (rs1)
        A2    : in  std_logic_vector(4 downto 0);   -- Direccion para el segundo registro fuente (rs2)
        Rd2   : out std_logic_vector(31 downto 0);  -- Dato del segundo registro fuente (rs2)
        A3    : in  std_logic_vector(4 downto 0);   -- Direccion para el registro destino (rd)
        Wd3   : in  std_logic_vector(31 downto 0);  -- Dato de entrada para el registro destino (rd)
        We3   : in  std_logic                       -- Habilitacion de la escritura de Wd3 (rd)
     ); 
  end component reg_bank;

  component control_unit
     port (
        -- Entrada = codigo de operacion en la instruccion:
        OpCode   : in  std_logic_vector (6 downto 0);
        -- Seniales para el PC
        Branch   : out  std_logic;                     -- 1 = Ejecutandose instruccion branch
        Ins_Jal  : out  std_logic;                     -- 1 = jal , 0 = otra instruccion, 
        Ins_Jalr : out  std_logic;                     -- 1 = jalr, 0 = otra instruccion, 
        -- Seniales relativas a la memoria y seleccion dato escritura registros
        ResultSrc: out  std_logic_vector(1 downto 0);  -- 00 salida Alu; 01 = salida de la mem.; 10 PC_plus4
        MemWrite : out  std_logic;                     -- Escribir la memoria
        MemRead  : out  std_logic;                     -- Leer la memoria
        -- Seniales para la ALU
        ALUSrc   : out  std_logic;                     -- 0 = oper.B es registro, 1 = es valor inm.
        AuiPcLui : out  std_logic_vector (1 downto 0); -- 0 = PC. 1 = zeros, 2 = reg1.
        ALUOp    : out  std_logic_vector (2 downto 0); -- Tipo operacion para control de la ALU
        -- Seniales para el GPR
        RegWrite : out  std_logic                      -- 1 = Escribir registro
     );
  end component;

  component alu_control is
    port (
      -- Entradas:
      ALUOp  : in std_logic_vector (2 downto 0);     -- Codigo de control desde la unidad de control
      Funct3 : in std_logic_vector (2 downto 0);     -- Campo "funct3" de la instruccion (I(14:12))
      Funct7 : in std_logic_vector (6 downto 0);     -- Campo "funct7" de la instruccion (I(31:25))     
      -- Salida de control para la ALU:
      ALUControl : out std_logic_vector (3 downto 0) -- Define operacion a ejecutar por la ALU
    );
  end component alu_control;

 component Imm_Gen is
    port (
        instr     : in std_logic_vector(31 downto 0);
        imm       : out std_logic_vector(31 downto 0)
    );
  end component Imm_Gen;

  signal Alu_Op1      : std_logic_vector(31 downto 0);
  signal Alu_Op2      : std_logic_vector(31 downto 0);
  signal AluControl   : std_logic_vector(3 downto 0);
  signal reg_RD_data  : std_logic_vector(31 downto 0);

  signal branch_true    : std_logic;
  signal PC_next        : std_logic_vector(31 downto 0);
  signal PC_reg         : std_logic_vector(31 downto 0);
  signal PC_plus4       : std_logic_vector(31 downto 0);

  signal Addr_Branchjal : std_logic_vector(31 downto 0);
  signal Addr_Jalr      : std_logic_vector(31 downto 0);
  signal decision_Jump  : std_logic;
  
  -- Forwarding Unit signals:
  signal Forward_Ctrl_A : std_logic_vector(1 downto 0);
  signal Forward_Ctrl_B : std_logic_vector(1 downto 0);
  signal Forward_Res_A  : std_logic_vector(31 downto 0);
  signal Forward_Res_B  : std_logic_vector(31 downto 0);

  -- Hazard Detection Unit signals:
  signal Write_IF_ID    : std_logic;
  signal PC_Write       : std_logic;
  signal Hazard_Ctrl    : std_logic;

  -- Branch Control signals:
  signal Flush_ID       : std_logic;
  signal Flush_EX       : std_logic;
  signal Flush_MEM       : std_logic;

  -- Pipeline signals:
  -- IF signals
  signal PC_plus4_IF    : std_logic_vector(31 downto 0);
  signal PC_IF          : std_logic_vector(31 downto 0);
  signal Instruction_IF : std_logic_vector(31 downto 0);

  -- ID signals
  signal PC_ID          : std_logic_vector(31 downto 0);
  signal PC_plus4_ID    : std_logic_vector(31 downto 0);
  signal Instruction_ID : std_logic_vector(31 downto 0);
  signal Ctrl_Branch_ID : std_logic;
  signal Ctrl_ResSrc_ID : std_logic_vector(1 downto 0);
  signal Ctrl_MemRead_ID  : std_logic;
  signal Ctrl_MemWrite_ID : std_logic;
  signal Ctrl_ALUOp_ID  : std_logic_vector(2 downto 0);
  signal Ctrl_ALUSrc_ID : std_logic;
  signal Ctrl_PcLui_ID  : std_logic_vector(1 downto 0);
  signal Ctrl_Jal_ID    : std_logic;
  signal Ctrl_Jalr_ID   : std_logic;
  signal Ctrl_RegWrite_ID : std_logic;
  signal Reg_RS1_ID      : std_logic_vector(31 downto 0);
  signal Reg_RS2_ID      : std_logic_vector(31 downto 0);
  signal Imm_Ext_ID    : std_logic_vector(31 downto 0);

  
  -- EX signals
  signal PC_EX          : std_logic_vector(31 downto 0);
  signal PC_plus4_EX    : std_logic_vector(31 downto 0);
  signal Instruction_EX : std_logic_vector(31 downto 0);
  signal Ctrl_Branch_EX : std_logic;
  signal Ctrl_ResSrc_EX : std_logic_vector(1 downto 0);
  signal Ctrl_MemRead_EX  : std_logic;
  signal Ctrl_MemWrite_EX : std_logic;
  signal Ctrl_ALUOp_EX  : std_logic_vector(2 downto 0);
  signal Ctrl_ALUSrc_EX : std_logic;
  signal Ctrl_PcLui_EX  : std_logic_vector(1 downto 0);
  signal Ctrl_Jal_EX    : std_logic;
  signal Ctrl_Jalr_EX   : std_logic;
  signal Ctrl_RegWrite_EX : std_logic;
  signal Reg_RS1_EX      : std_logic_vector(31 downto 0);
  signal Reg_RS2_EX      : std_logic_vector(31 downto 0);
  signal Imm_Ext_EX      : std_logic_vector(31 downto 0);
  signal Alu_Res_EX      : std_logic_vector(31 downto 0);
  signal Alu_ZERO_EX     : std_logic;
  signal Alu_SIGN_EX     : std_logic;
  signal Addr_Jump_dest_EX : std_logic_vector(31 downto 0);

-- MEM signals 
  signal PC_plus4_MEM     : std_logic_vector(31 downto 0);
  signal Instruction_MEM  : std_logic_vector(31 downto 0);
  signal Ctrl_Branch_MEM  : std_logic;
  signal Ctrl_ResSrc_MEM  : std_logic_vector(1 downto 0);
  signal Ctrl_MemRead_MEM : std_logic;
  signal Ctrl_MemWrite_MEM: std_logic;
  signal Ctrl_Jal_MEM     : std_logic;
  signal Ctrl_Jalr_MEM    : std_logic;
  signal Ctrl_RegWrite_MEM: std_logic;
  signal Reg_RS2_MEM      : std_logic_vector(31 downto 0);
  signal Alu_Res_MEM      : std_logic_vector(31 downto 0);
  signal Alu_ZERO_MEM     : std_logic;
  signal Alu_SIGN_MEM     : std_logic;
  signal Read_Data_MEM    : std_logic_vector(31 downto 0);
  signal Addr_Jump_dest_MEM : std_logic_vector(31 downto 0);


  -- WB signals 
  signal PC_plus4_WB      : std_logic_vector(31 downto 0);
  signal Instruction_WB   : std_logic_vector(31 downto 0);
  signal Ctrl_ResSrc_WB   : std_logic_vector(1 downto 0);
  signal Ctrl_RegWrite_WB : std_logic;
  signal Alu_Res_WB       : std_logic_vector(31 downto 0);
  signal Read_Data_WB     : std_logic_vector(31 downto 0);

begin


  -- Program Counter
  PC_reg_proc: process(Clk, Reset, PC_Write, PC_next)
  begin
    if Reset = '1' then
      PC_reg <= (22 => '1', others => '0'); -- 0040_0000
    elsif rising_edge(Clk) and PC_Write = '1' then
      PC_reg <= PC_next;
    end if;
  end process;
  

  -- Program Counter singals
  PC_IF          <= PC_reg;
  PC_plus4       <= PC_reg + 4;
  PC_plus4_IF    <= PC_plus4;
  PC_next        <= Addr_Jump_dest_MEM when decision_Jump = '1' else PC_plus4_IF;

  -- Instruction memory
  IAddr          <= PC_reg;
  Instruction_IF <= IDataIn;

  RegsRISCV : reg_bank
  port map (
    Clk   => Clk,
    Reset => Reset,
    A1    => Instruction_ID(19 downto 15), --rs1
    Rd1   => Reg_RS1_ID,
    A2    => Instruction_ID(24 downto 20), --rs2
    Rd2   => Reg_RS2_ID,
    A3    => Instruction_WB(11 downto 7), 
    Wd3   => reg_RD_data,
    We3   => Ctrl_RegWrite_WB
  );

  UnidadControl : control_unit
  port map(
    OpCode   => Instruction_ID(6 downto 0),
    -- Señales para el PC
    Branch   => Ctrl_Branch_ID,
    Ins_Jal  => Ctrl_Jal_ID,
    Ins_Jalr => Ctrl_Jalr_ID,
    -- Señales para la memoria y seleccion dato escritura registros
    ResultSrc=> Ctrl_ResSrc_ID,
    MemWrite => Ctrl_MemWrite_ID,
    MemRead  => Ctrl_MemRead_ID,
    -- Señales para la ALU
    ALUSrc   => Ctrl_ALUSrc_ID,
    AuiPcLui => Ctrl_PcLui_ID,
    ALUOp    => Ctrl_ALUOp_ID,
    -- Señales para el GPR
    RegWrite => Ctrl_RegWrite_ID
  );

  immed_op : Imm_Gen
  port map (
        instr    => Instruction_ID(31 downto 0),
        imm      => Imm_Ext_ID 
  );

  Addr_BranchJal <= PC_EX  + Imm_Ext_EX;
  Addr_Jalr      <= Reg_RS1_EX + Imm_Ext_EX;
  -- Jump decision multiplexors
  decision_Jump  <= Ctrl_Jal_MEM or Ctrl_Jalr_MEM or (Ctrl_Branch_MEM and branch_true);
  branch_true    <= '1' when ( ((Instruction_MEM(14 downto 12) = BR_F3_BEQ) and (Alu_ZERO_MEM = '1')) or
                               ((Instruction_MEM(14 downto 12) = BR_F3_BNE) and (Alu_ZERO_MEM = '0')) or
                               ((Instruction_MEM(14 downto 12) = BR_F3_BLT) and (Alu_SIGN_MEM = '1')) or
                               ((Instruction_MEM(14 downto 12) = BR_F3_BGE) and (Alu_SIGN_MEM = '0')) ) else
                    '0';
 
  Addr_Jump_dest_EX <= Addr_Jalr when Ctrl_Jalr_EX = '1' else
                    Addr_BranchJal when (Ctrl_Branch_EX='1') or (Ctrl_Jal_EX='1') else
                    (others =>'0');

  Alu_control_i: alu_control
  port map(
    -- Entradas:
    ALUOp   => Ctrl_ALUOp_EX, -- Codigo de control desde la unidad de control
    Funct3  => Instruction_EX(14 downto 12),    -- Campo "funct3" de la instruccion
    Funct7  => Instruction_EX(31 downto 25),    -- Campo "funct7" de la instruccion
    -- Salida de control para la ALU:
    ALUControl => AluControl -- Define operacion a ejecutar por la ALU
  );

  Alu_RISCV : alu_RV
  port map (
    OpA      => Alu_Op1,
    OpB      => Alu_Op2,
    Control  => AluControl,
    Result   => Alu_Res_EX,
    SignFlag => Alu_SIGN_EX,
    CarryOut => open,
    ZFlag    => Alu_ZERO_EX
  );
  
  -- Alu operands
  Alu_Op1 <= PC_EX when Ctrl_PcLui_EX = "00" else
             (others => '0')  when Ctrl_PcLui_EX = "01" else
             Forward_Res_A;
          
  Alu_Op2 <= Forward_Res_B when Ctrl_ALUSrc_EX = '0' else
             Imm_Ext_EX;

  -- Data memory
  DAddr      <= Alu_Res_MEM;
  DDataOut   <= Reg_RS2_MEM;
  DWrEn      <= Ctrl_MemWrite_MEM;
  DRdEn      <= Ctrl_MemRead_MEM;
  Read_Data_MEM <= DDataIn;
  
  -- Result mux
  reg_RD_data <= Read_Data_WB when Ctrl_ResSrc_WB = "01" else
                 PC_plus4_WB  when Ctrl_ResSrc_WB = "10" else 
                 Alu_Res_WB; -- When 00

  -- Forwarding Unit
  Forward_Ctrl_A <= "10" when (Ctrl_RegWrite_MEM = '1') and not (Instruction_MEM(11 downto 7) = "00000")
                    and (Instruction_MEM(11 downto 7) = Instruction_EX(19 downto 15)) else
                    "01" when (Ctrl_RegWrite_WB = '1') and not (Instruction_WB(11 downto 7) = "00000")
                    and (Instruction_WB(11 downto 7) = Instruction_EX(19 downto 15)) else "00";
  Forward_Ctrl_B <= "10" when (Ctrl_RegWrite_MEM = '1') and not (Instruction_MEM(11 downto 7) = "00000")
                    and (Instruction_MEM(11 downto 7) = Instruction_EX(24 downto 20)) else
                    "01" when (Ctrl_RegWrite_WB = '1') and not (Instruction_WB(11 downto 7) = "00000")
                    and (Instruction_WB(11 downto 7) = Instruction_EX(24 downto 20)) else "00";

  Forward_Res_A <= Reg_RS1_EX  when Forward_Ctrl_A = "00" else
                   Alu_Res_MEM when Forward_Ctrl_A = "10" else 
                   reg_RD_data when Forward_Ctrl_A = "01" else
                   x"00000000";

  Forward_Res_B <= Reg_RS2_EX  when Forward_Ctrl_B = "00" else
                   Alu_Res_MEM when Forward_Ctrl_B = "10" else 
                   reg_RD_data when Forward_Ctrl_B = "01" else 
                   x"00000000";
    
  -- Hazard Detection Unit
  Hazard_Ctrl <= '1' when Ctrl_MemRead_EX = '1' and ((Instruction_EX(11 downto 7) = Instruction_ID(19 downto 15)) or
                 (Instruction_EX(11 downto 7) = Instruction_ID(24 downto 20))) else '0';
  PC_Write    <= '0' when (Hazard_Ctrl = '1') else '1';
  Write_IF_ID <= '0' when (Hazard_Ctrl = '1') else '1'; 

  -- Branch Control
  Flush_ID <= '1' when decision_Jump = '1' and (branch_true = '1' and Ctrl_Branch_MEM = '1') else '0';
  Flush_EX <= '1' when decision_Jump = '1' and (branch_true = '1' and Ctrl_Branch_MEM = '1') else '0'; 
  Flush_MEM <= '1' when decision_Jump = '1' and (branch_true = '1' and Ctrl_Branch_MEM = '1') else '0'; 

----------------------------------------------------------------------------
----------------------------------------------------------------------------
-- Pipeline reg: IF/ID
  IF_ID_reg: process(Clk, Reset, Write_IF_ID, PC_IF, Instruction_IF, PC_plus4_IF, Flush_ID)
  begin 
    if Reset = '1' then
      PC_ID <= (others=>'0');
      Instruction_ID <= (others=>'0');
      PC_plus4_ID <= (others=>'0');
    elsif rising_edge(Clk) and Write_IF_ID = '1' then
      PC_ID <= PC_IF;
      Instruction_ID <= Instruction_IF;
      PC_plus4_ID <= PC_plus4_IF;
      if Flush_ID = '1' then
        Instruction_ID <= x"00000013";
      end if;
    end if;
  end process;

----------------------------------------------------------------------------
----------------------------------------------------------------------------
-- Pipeline reg: ID/EX
  ID_EX_reg: process(clk, reset)
    begin
      if reset = '1' then 
        PC_EX          <= (others=>'0');
        Instruction_EX <= (others=>'0');
        PC_plus4_EX    <= (others=>'0');
        Ctrl_Branch_EX <= '0';
        Ctrl_ResSrc_EX <= (others=>'0');
        Ctrl_MemRead_EX  <= '0';
        Ctrl_MemWrite_EX <= '0';
        Ctrl_ALUOp_EX  <= (others=>'0');
        Ctrl_ALUSrc_EX <= '0';
        Ctrl_PcLui_EX  <= (others=>'0');
        Ctrl_Jal_EX    <= '0';
        Ctrl_Jalr_EX   <= '0';
        Ctrl_RegWrite_EX <= '0';
        Reg_RS1_EX     <= (others=>'0');
        Reg_RS2_EX     <= (others=>'0');
        Imm_Ext_EX     <= (others=>'0');
      elsif rising_edge(clk) then
        PC_EX <= PC_ID;          
        PC_plus4_EX <= PC_plus4_ID;     
        Instruction_EX <= Instruction_ID;
        Imm_Ext_EX <= Imm_Ext_ID;
        Reg_RS1_EX <= Reg_RS1_ID;
        Reg_RS2_EX <= Reg_RS2_ID;

        if Hazard_Ctrl = '0' and Flush_EX = '0' then
          Ctrl_Branch_EX <= Ctrl_Branch_ID;
          Ctrl_ResSrc_EX <= Ctrl_ResSrc_ID;
          Ctrl_MemRead_EX <= Ctrl_MemRead_ID;
          Ctrl_MemWrite_EX <= Ctrl_MemWrite_ID;
          Ctrl_ALUOp_EX <= Ctrl_ALUOp_ID;
          Ctrl_ALUSrc_EX <= Ctrl_ALUSrc_ID; 
          Ctrl_PcLui_EX <= Ctrl_PcLui_ID;
          Ctrl_Jal_EX <= Ctrl_Jal_ID;    
          Ctrl_Jalr_EX <= Ctrl_Jalr_ID;
          Ctrl_RegWrite_EX <= Ctrl_RegWrite_ID;
        else
          Ctrl_Branch_EX <= '0';
          Ctrl_ResSrc_EX <= (others=>'0');
          Ctrl_MemRead_EX  <= '0';
          Ctrl_MemWrite_EX <= '0';
          Ctrl_ALUOp_EX  <= (others=>'0');
          Ctrl_ALUSrc_EX <= '0';
          Ctrl_PcLui_EX  <= (others=>'0');
          Ctrl_Jal_EX    <= '0';
          Ctrl_Jalr_EX   <= '0';
          Ctrl_RegWrite_EX <= '0';
        end if;
      end if; 
  end process;
----------------------------------------------------------------------------
----------------------------------------------------------------------------
-- Pipeline reg: EX/MEM
  EX_MEM_reg: process(clk, reset)
  begin
    if reset = '1' then
      Instruction_MEM <= (others=>'0');
      PC_plus4_MEM <= (others=>'0');
      Ctrl_Branch_MEM <= '0';
      Ctrl_ResSrc_MEM <= (others=>'0');
      Ctrl_MemRead_MEM <= '0';
      Ctrl_MemWrite_MEM <= '0';
      Ctrl_Jal_MEM <= '0';
      Ctrl_Jalr_MEM <= '0';
      Ctrl_RegWrite_MEM <= '0';
      Reg_RS2_MEM <= (others=>'0'); -- WriteData
      Alu_Res_MEM <= (others=>'0');
      Alu_ZERO_MEM <= '0';
      Alu_SIGN_MEM <= '0';
      Addr_Jump_dest_MEM <= (others=>'0');
    elsif rising_edge(clk) then
      Instruction_MEM <= Instruction_EX;
      PC_plus4_MEM <= PC_plus4_EX;
      Reg_RS2_MEM <= Forward_Res_B;
      Alu_Res_MEM <= Alu_Res_EX;
      Alu_ZERO_MEM <= Alu_ZERO_EX;
      Alu_SIGN_MEM <= Alu_SIGN_EX;
      Addr_Jump_dest_MEM <= Addr_Jump_dest_EX;

        if Flush_MEM = '0' then
          Ctrl_Branch_MEM <= Ctrl_Branch_EX;
          Ctrl_ResSrc_MEM <= Ctrl_ResSrc_EX;
          Ctrl_MemRead_MEM <= Ctrl_MemRead_EX;
          Ctrl_MemWrite_MEM <= Ctrl_MemWrite_EX;
          Ctrl_Jal_MEM <= Ctrl_Jal_EX;
          Ctrl_Jalr_MEM <= Ctrl_Jalr_EX;
          Ctrl_RegWrite_MEM <= Ctrl_RegWrite_EX;
        else 
          Ctrl_Branch_MEM <= '0';
          Ctrl_ResSrc_MEM <= (others=>'0');
          Ctrl_MemRead_MEM <= '0';
          Ctrl_MemWrite_MEM <= '0';
          Ctrl_Jal_MEM <= '0';
          Ctrl_Jalr_MEM <= '0';
          Ctrl_RegWrite_MEM <= '0';
        end if;
    end if;  
  end process;
----------------------------------------------------------------------------
----------------------------------------------------------------------------
-- Pipeline reg: WB/MEM
  MEM_WB_reg: process(clk, reset)
  begin
    if reset = '1' then
      Instruction_WB <= (others=>'0');
      PC_plus4_WB <= (others=>'0');
      Ctrl_ResSrc_WB <= (others=>'0');
      Ctrl_RegWrite_WB <= '0';
      Read_Data_WB <= (others=>'0');
      Alu_Res_WB <= (others=>'0');
    elsif rising_edge(clk) then
      Instruction_WB <= Instruction_MEM;
      PC_plus4_WB <= PC_plus4_MEM;
      Ctrl_ResSrc_WB <= Ctrl_ResSrc_MEM;
      Ctrl_RegWrite_WB <= Ctrl_RegWrite_MEM;
      Alu_Res_WB <= Alu_Res_MEM;
      Read_Data_WB <= Read_Data_MEM;
    end if;  
  end process;
end architecture;

