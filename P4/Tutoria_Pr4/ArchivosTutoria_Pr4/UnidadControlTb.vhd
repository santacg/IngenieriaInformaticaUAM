--------------------------------------------------------------------
----------------------------------------------------------------------
-- Asignatura: Estructura de Computadores
-- Fichero: UnidadControlTb.vhd
-- Descripción: Testbench para la unidad de Control para el microprocesador MIPS
-- Fichero de apoyo para: Práctica: 4. Ejercicio: 2b
----------------------------------------------------------------------
----------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
 
entity UnidadControlTb is
end UnidadControlTb;
 
architecture behavior of UnidadControlTb is
 
   type CasosPruebaT is record
		instruccion : std_logic_vector(31 downto 0);
		sigControl :  std_logic_vector(7 downto 0);
		aluControl : std_logic_vector(2 downto 0);
	end record;

	component UnidadControl
	port(
		OPCode : in  std_logic_vector (5 downto 0); -- OPCode de la instrucción
		Funct : in std_logic_vector(5 downto 0); -- Funct de la instrucción
		-- Señales para el PC
		Jump : out  std_logic;
		Branch : out  std_logic;
		-- Señales para la memoria
		MemToReg : out  std_logic;
		MemWrite : out  std_logic;
		
		-- Señales para la ALU
		ALUSrc : out  std_logic;
		ALUControl : out  std_logic_vector (2 downto 0);
		ExtCero : out std_logic;
		
		-- Señales para el GPR
		RegWrite : out  std_logic;
		RegDst : out  std_logic
        );
	end component;

	
   --Inputs
   signal OPCode : std_logic_vector(5 downto 0) := (others => '0');
   signal funct : std_logic_vector(5 downto 0) := (others => '0');
   

 	--Outputs
   signal regWrite, ALUSrc, branch, memWrite, memToReg, jump, extCero, RegDst : std_logic;
	signal ALUControl : std_logic_vector(2 downto 0);
   
	
	signal controlFlags : std_logic_vector(7 downto 0);
	
	signal instruc : std_logic_vector(31 downto 0);
	
	-- numero de casos de prueba
	constant NUMCASOSPRUEBA : integer := 16;

	-- matriz de casos de prueba
	type CasosPruebaT2 is array (1 to NUMCASOSPRUEBA) of CasosPruebaT;

  
  constant casos_prueba : CasosPruebaT2 := (

	-- 1 INSTRUCCION: lw
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"8c012000",	"11001000",	"010"),
	-- 2 INSTRUCCION: sw
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"ac012004",	"0101-00-",	"010"),
	-- 3 INSTRUCCION: add
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"00430820",	"100000-1",	"010"),
	-- 4 INSTRUCCION: sub
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"00430822",	"100000-1",	"110"),
	-- 5 INSTRUCCION: and
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"00000024",	"100000-1",	"000"),
	-- 6 INSTRUCCION: or
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"00221825",	"100000-1",	"001"),
	-- 7 INSTRUCCION: slt
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"0000002a",	"100000-1",	"111"),
	-- 8 INSTRUCCION: xor
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"00437026",	"100000-1",	"011"),
	-- 9 INSTRUCCION: beq
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"10000002",	"0010-0--",	"110"),
	-- 10 INSTRUCCION: j
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"0800000b",	"0--0-1--",	"---"),
	-- INSTRUCCIONES ALU INMEDIATAS
	-- 11 INSTRUCCION: addi
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"20410005",	"11000000",	"010"),
	-- 12 INSTRUCCION: andi
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"30410005",	"11000010",	"000"),
	-- 13 INSTRUCCION: ori
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"34230001",	"11000010",	"001"),
	-- 14 INSTRUCCION: slti
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"28410005",	"11000000",	"111"),
	-- 15 INSTRUCCION: xori
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"3A110041",	"11000010",	"011"),
	-- 16 INSTRUCCION: nor
	--	INSTRUCCION		SENIALES_CTRL	ALU_OP
	(	x"02A4A027",	"100000-1",	"101")
	);
	
	 -- Tiempo que vamos a esperar a que conteste la UC  
  constant tdelay : time := 10 ns;
  
  signal fallos : integer := 0;
  
BEGIN
 

   uut: UnidadControl PORT MAP (
      OPCode => OPCode,
		Funct => funct,
		jump => jump,
		Branch => branch,
		MemToReg => memToReg,
		MemWrite => memWrite,
		ALUSrc => ALUSrc,
		ALUControl => ALUControl,
		ExtCero => extCero,
		RegWrite => regWrite,
		RegDst => RegDst
	);
 
	controlFlags <= regWrite & ALUSrc & branch & memWrite & memToReg & jump & extCero & RegDst;

   -- Stimulus process
	stim_proc: process
   begin		
	     
		for i in 1 to NUMCASOSPRUEBA loop
		
			OPCode <= casos_prueba(i).INSTRUCCION(31 downto 26);
			funct <= casos_prueba(i).INSTRUCCION(5 downto 0);
			
			instruc <= casos_prueba(i).INSTRUCCION;
			
			wait for tdelay;
			
			assert std_match(controlFlags,casos_prueba(i).sigControl)
			report "fallo en seniales caso " & integer'image(i) & " de instrucciones"
			severity warning;
			
			if not std_match(controlFlags,casos_prueba(i).sigControl) then
				fallos <= fallos + 1;
			end if;
			
			assert std_match(aluControl,casos_prueba(i).aluControl)
			report "fallo en ALUControl caso " & integer'image(i) & " de instrucciones"
			severity warning;
			
			if not std_match(ALUControl,casos_prueba(i).aluControl) then
				fallos <= fallos + 1;
			end if;
			
			
		end loop;
		
		wait for tdelay;
		
		report "Simulación finalizada";
		report "Número de fallos: " & integer'image(fallos) severity note;
				
		wait;
	end process;

END;
