----------------------------------------------------------------------
----------------------------------------------------------------------
-- Asignatura: Estructura de Computadores
-- Fichero: MicroMIPS.vhd
-- Descripción: Diseño del microprocesador MIPS
-- Fichero de apoyo para: Práctica: 4. Ejercicio: 3
----------------------------------------------------------------------
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity MicroMIPS is	port (
	Clk : in std_logic; -- Reloj
	NRst : in std_logic; -- Reset activo a nivel bajo
	MemProgAddr : out unsigned(31 downto 0); -- Dirección para la memoria de programa
	MemProgData : in unsigned(31 downto 0); -- Código de operación
	MemDataAddr : out unsigned(31 downto 0); -- Dirección para la memoria de datos
	MemDataDataRead : in signed(31 downto 0); -- Dato a leer en la memoria de datos
	MemDataDataWrite : out signed(31 downto 0); -- Dato a guardar en la memoria de datos
	MemDataWE : out std_logic
	);
end MicroMIPS;
 
architecture Practica3 OF MicroMIPS is
 

begin


end Practica3;