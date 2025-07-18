----------------------------------------------------------------------
-- Fichero: Registro.vhd
-- Descripci�n: Registro de 1 bit con Chip Enable
-- Fecha �ltima modificaci�n: 2020-01-29
-- Asignatura: E.C. 1� grado
-- Grupo de Pr�cticas:
-- Grupo de Teor�a:
-- Pr�ctica: 1
-- Ejercicio: 2
----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.numeric_std.ALL;


--Definici�n de la entidad
--El registro tiene una entrada de datos (D), entrada de Reset, CLK y CE
--Como salida tiene un s�lo bit (Q)

entity Registro is
    port ( 
		D : in  std_logic;
		Reset : in  std_logic;
		Clk : in  std_logic;
		Ce : in  std_logic;
		Q : out  std_logic
	);
end Registro;

architecture Practica of Registro is
	
begin

	--El registro es sensible al Reset (as�ncrono) y a la se�al del reloj
	process (all)
	begin
		-- Si el reset est� activo la salida vale 0
		if Reset = '1' then
			Q <= '0';
		-- Si hay un flanco de subida del reloj
		elsif rising_edge (Clk) then
			-- Si el chip enable est� activo
			if Ce = '1' then
				Q <= D;
			end if;
		end if;
	end process;

end Practica;
