----------------------------------------------------------------------
----------------------------------------------------------------------
-- Asignatura: Estructura de Computadores. GII. 1er curso.
-- Fichero: MicroSuma.vhd
-- Descripci�n: Micro MIPS muy simplificado, s�lo suma con dato inmediato
-- Fichero de apoyo para: Pr�ctica: 2. Ejercicio: 3
----------------------------------------------------------------------
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity MicroSuma is
	port (
		Clk : in std_logic; -- Reloj
		NRst : in std_logic; -- Reset activo a nivel bajo
		MemProgAddr : out unsigned(31 downto 0); -- Direcci�n para la memoria de programa
		MemProgData : in unsigned(31 downto 0) -- C�digo de operaci�n
	);
	end MicroSuma;

architecture Practica of MicroSuma is

	-- Declaraci�n de RegsMIPS
	
	-- Declaraci�n de ALUMIPS
	
	-- Declaraci�n de se�ales auxiliares

begin

	-- Instancia de RegMIPS
	
	-- Instancia de ALUMIPS
	
	-- Extensi�n de signo
	
	-- Ruta del PC


end Practica;

