----------------------------------------------------------------------
----------------------------------------------------------------------
-- Asignatura: Estructura de Computadores. GII. 1er curso.
-- Fichero: MemProgMIPS.vhd
-- Descripci�n: Memoria de programa para el MIPS
-- Fichero de apoyo para: Pr�ctica: 4. Ejercicio: 1. Se utiliza en la validaci�n del Ejercicio 3
----------------------------------------------------------------------
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.numeric_std.ALL;

entity MemProgMIPS is										-- Al crear el archivo MemProgVectores.asm, debe cambiar el nombre de la entidad
	port (
		MemProgAddr : in unsigned(31 downto 0);        		-- Direcci�n para la memoria de programa
		MemProgData : out unsigned(31 downto 0) 			-- Instrucci�n
	);
end MemProgMIPS;											-- Al crear el archivo MemProgVectores.asm, debe cambiar el nombre de la entidad

architecture Simple of MemProgMIPS is						-- Al crear el archivo MemProgVectores.asm, debe cambiar el nombre de la entidad

begin

	-- Proceso para la escritura inicial en la memoria de c�digo o de programa. 
	-- La memoria de programa es de solo lectura, el proceso de escritura es �nico y se realiza antes de ejecutar la simulaci�n.  
	EscrituraMemProg: process(all)
	begin
		-- La memoria devuelve un valor para cada direcci�n.
		-- Estos valores son los c�digos de programa de cada instrucci�n, estando situado cada uno en su direcci�n.
		-- C�digo para la escritura de los datos iniciales quedeben ser cargados previamente en memoria antes de la ejecuci�n del programa.
		
		-- Se cargan a partir de una direcci�n dada, en MARS por la directiva .text, que en el ejemplo es 0x00000000.
		-- Como cada instruccci�n ocupa 4 bytes, las direcciones se incrementan de 4 en 4. 
			
		case MemProgAddr is
-- *********************************************************************
			when x"00000000" => MemProgData <= x"20010004";		-- addi $1, $0, 0x0004. 	Resultado $1 = 0x00000004
			when x"00000004" => MemProgData <= x"3402000f";		-- ori, $2, $0, 0x000F. 	Resultado $2 = 0x0000000F
			when x"00000008" => MemProgData <= x"30430004";		-- andi $3, $2, 0x0004. 	Resultado $3 = 0x00000004
			when x"0000000C" => MemProgData <= x"2064ffec";		-- addi $4, $3, -20. 		Resultado $4 = 0xFFFFFFF0
			when x"00000010" => MemProgData <= x"28057fff";		-- slti $5, $0, 0x7FFF. 	Resultado $5 = 0x00000001
			when x"00000014" => MemProgData <= x"2805ffff";		-- slti $5, $0, -1.     	Resultado $5 = 0x00000000
			when x"00000018" => MemProgData <= x"0081302a";		-- slt $6, $4, $1.      	Resultado $6 = 0x00000001 
			when x"0000001C" => MemProgData <= x"8c072000"; 	-- lw $7, A($0). 			Lee de la posici�n dememoria 0x2000. Resultado $7 = 0x0000000A
			when x"00000020" => MemProgData <= x"8c282000";		-- lw $8, A($1). 			Lee de la posici�n de memoria 0x2004. Resultado $8 = 0x00000009
			when x"00000024" => MemProgData <= x"8c092008";		-- lw $9, C($0). 			Lee de la posici�n de memoria 0x2008. Resultado $9 = 0x00000009
			when x"00000028" => MemProgData <= x"01075022";		-- sub $10, $8, $7.     	Resultado $10 = 0xFFFFFFFF
			when x"0000002C" => MemProgData <= x"10240001";		-- beq $1, $4, 0x0001. 		No salta
			when x"00000030" => MemProgData <= x"11090001";		-- beq $8, $9, 0x0001. 		Salta a addi $11,$10,0x0002
			when x"00000034" => MemProgData <= x"00005020";		-- add $10, $0, $0.    		Resultado $10 = 0x00000000
			when x"00000038" => MemProgData <= x"214b0002";		-- addi $11, $10,0x0002. 	Resultado $11 = 0x00000002
			when x"0000003C" => MemProgData <= x"00016020";		-- add $12, $0, $1. 		Resultado $12 = 0x00000004
			when x"00000040" => MemProgData <= x"ac0c200c";		-- sw $12, R($0). 			Resultado escribe en Mem[200C] = 0x00000004
			when x"00000044" => MemProgData <= x"8c0d200c";		-- lw $13, R($0). 			Lee de la posici�n de memoria 0x200C. Resultado $13 = 0x00000004
			when x"00000048" => MemProgData <= x"00217027";		-- nor $14, $1, $1. 		Resultado $14 = 0xFFFFFFFB
			when x"0000004C" => MemProgData <= x"01CE1026";		-- xor $2, $14, $14 		Resultado $2 = 0x00000000
			when x"00000050" => MemProgData <= x"01c07024";		-- and $14, $14, $0 		Resultado $14 = 0x00000000
			when x"00000054" => MemProgData <= x"00227825";		-- or $15, $1, $2.			Resultado $15 = 0x0000000F
			when x"00000058" => MemProgData <= x"39f00013";		-- xori $16, $15, 0x13.			Resultado $16 = 0x0000001C
			when x"0000005C" => MemProgData <= x"08000017";		-- fin: j fin. 				Bucle infinito
-- *********************************************************************			
			when others => MemProgData <= x"00000000"; -- Resto de memoria vac�a
		end case;
	end process EscrituraMemProg;

		-- Al crear el archivo con la memoria MemProgVectores, debe cambiar los c�digos de este archivo y sustituirlos por los 
		-- correspondientes del programa vectores.asm, asociando cada c�digo a la direcci�n correspondiente.
	
end Simple;

