----------------------------------------------------------------------
----------------------------------------------------------------------
-- Asignatura: Estructura de Computadores. GII. 1er curso.
-- Fichero: MemProgSuma.vhd
-- Descripci�n: Memoria de programa para el MIPS. Contiene sumas entre un registro y un dato inmediato
-- Fichero de apoyo para: Pr�ctica: 2. Ejercicio: 3
----------------------------------------------------------------------
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;


entity MemProgSuma is
	port (
		MemProgAddr : in unsigned(31 downto 0); -- Direcci�n para la memoria de programa
		MemProgData : out unsigned(31 downto 0) -- C�digo de operaci�n
	);
end MemProgSuma;

architecture Simple of MemProgSuma is

begin

	LecturaMemProg: process(MemProgAddr)
	begin
		-- La memoria devuelve un valor para cada direcci�n.
		-- Estos valores son los c�digos de programa de cada instrucci�n,
		-- estando situado cada uno en su direcci�n.
		case MemProgAddr is
			when X"00000000" => MemProgData <= X"2001000a";		-- R1 = R0 + 10
			when X"00000004" => MemProgData <= X"20220005";		-- R2 = R1 + 5
			when X"00000008" => MemProgData <= X"20430019";		-- R3 = R2 + 25	
			when X"0000000C" => MemProgData <= X"20000005";		-- R0 = R0 + 5
			when X"00000010" => MemProgData <= X"2064FFFB";		-- R4 = R3 -  5
			when others => MemProgData <= X"00000000"; 				-- Resto de memoria vac�a
		end case;
	end process LecturaMemProg;

end Simple;
