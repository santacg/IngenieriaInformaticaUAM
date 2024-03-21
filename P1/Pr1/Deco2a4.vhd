----------------------------------------------------------------------
-- Fichero: Deco2a4.vhd
-- Descripción: Decodificador 2 a 4
-- Fecha última modificación: 2020-01-29
-- Asignatura: E.C. 1º grado
-- Grupo de Prácticas:
-- Grupo de Teoría:
-- Práctica: 1
-- Ejercicio: 2
----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.numeric_std.ALL;



entity Deco2a4 is
    port ( 
		D : in  std_logic_vector (1 downto 0);
		Q : out  std_logic_vector (3 downto 0)
	);
end Deco2a4;

architecture Practica of Deco2a4 is

begin

	process (all)
	begin
		case D is
			when "00" => Q <= "0001";
			when "01" => Q <= "0010";
			when "10" => Q <= "0100";
			when others => Q <= "1000";
		end case;
	end process;

end Practica;
