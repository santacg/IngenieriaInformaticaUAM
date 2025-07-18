----------------------------------------------------------------------
-- Fichero: Contador.vhd
-- Descripci�n: Contador de 8 bits con Chip Enable y selector de sentido de cuenta
-- Fecha �ltima modificaci�n: 2020-01-29 
-- Asignatura: E.C. 1� grado
-- Grupo de Pr�cticas:
-- Grupo de Teor�a:
-- Pr�ctica: 1
-- Ejercicio: 3
----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.numeric_std.ALL;


-- Contador de 8 bits
entity Contador is
    Port (
		Clk : in  STD_LOGIC;
		Reset : in  STD_LOGIC;
		Ce : in  STD_LOGIC;
		Up : in  STD_LOGIC;
		Q : out  UNSIGNED (7 downto 0)
	);
end Contador;

architecture Practica of Contador is

	begin


	process (all)
	begin
		if Reset = '1' then
			Q <= (OTHERS => '0');
		elsif rising_edge(Clk) then
			if Ce = '1' then
				if Up = '1' then
					Q <= Q + 1;
				else
					Q <= Q - 1;
				end if;
			end if;
		end if;


	end process;

end Practica;
