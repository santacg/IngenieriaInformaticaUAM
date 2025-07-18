----------------------------------------------------------------------
----------------------------------------------------------------------
-- Asignatura: Estructura de Computadores. GII. 1er curso.
-- Fichero: MemDataMIPS.vhd
-- Descripci�n: Memoria de datos para el MIPS
-- Fichero de apoyo para: Pr�ctica: 4. Ejercicio: 1. Se utiliza en la validaci�n del Ejercicio 3
----------------------------------------------------------------------
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;


entity MemDataMIPS is								
	port (
		Clk : in std_logic;
		NRst : in std_logic;
		MemDataAddr : in unsigned(31 downto 0);
		MemDataDataWrite : in signed(31 downto 0);
		MemDataWE : in std_logic;
		MemDataDataRead : out signed(31 downto 0)
	);
end MemDataMIPS;									

architecture Simple of MemDataMIPS is				

  -- 4 GB son 1 gigapalabras, pero el simulador no deja tanta memoria
  -- Dejamos 64 kB (16 kpalabras), usamos los 16 LSB
  type Memoria is array (0 to (2**14)-1) of signed(31 downto 0);
  signal memData : Memoria;

begin

	-- Proceso para la escritura s�ncrona en la memoria de datos.
EscrituraMemData: process(all)
	begin
		-- Con el reset activo, se inicializa a ceros todas las posiciones de la memoria de datos. 
	if NRst = '0' then
		for i in 0 to (2**14)-1 loop
			memData(i) <= (others => '0');
		end loop;

		-- C�digo para la escritura de los datos iniciales quedeben ser cargados previamente en memoria antes de la ejecuci�n del programa.
		
		-- Se cargan a partir de una direcci�n determinada, en MARS dada por la directiva .data. En este caso en la direcci�n 0x00002000.
		-- Para que se entienda como un �ndice entero, la direcci�n 0x2000 se debe escribir como 16#2000#.
		-- Como cada dato ocupa 4 bytes, el �ndice entero debe ir dividido por 4.
 
--      *************************************************
				memData(16#2000#/4) <= x"0000000A";
				memData(16#2004#/4) <= x"00000009";
				memData(16#2008#/4) <= x"00000009";
--      *************************************************
		
		
	-- C�digo para la escritura s�ncrona en la memoria de datos durante la ejecuci�n del programa (instrucci�n sw)	
	elsif rising_edge(Clk) then
		-- En este caso se escribe por flanco de bajada para que sea
		-- a mitad de ciclo y todas las se�ales est�n estables
		if MemDataWE = '1' then
			memData((to_integer(MemDataAddr)/4)) <= MemDataDataWrite;
		end if;
	end if;
	end process EscrituraMemData;

	-- Proceso para la lectura combinacional, siempre activa, desde la memoria de datos
	-- Cada vez se devuelve una palabra completa, que ocupa 4 bytes
LecturaMemData: process(all)
	begin
		-- Parte baja de la memoria s� est�, se lee tal cual
		if MemDataAddr(31 downto 16) = x"0000" then
			MemDataDataRead <= MemData((to_integer(MemDataAddr)/4));
		else -- Parte alta no existe, se leen ceros
			MemDataDataRead <= (others => '0');
		end if;
	end process LecturaMemData;

end Simple;

