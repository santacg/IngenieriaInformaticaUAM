----------------------------------------------------------------------
----------------------------------------------------------------------
-- Asignatura: Estructura de Computadores. GII. 1er curso.
-- Fichero: MicroMIPSTb.vhd
-- Descripci�n: Banco de pruebas para el microprocesador MIPS
-- Fichero de apoyo para: Pr�ctica: 4. Ejercicio: 3
-- Este archivo sirve para la primera prueba de validaci�n del Ejercicio 3
----------------------------------------------------------------------
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity MicroMIPSTb is
end MicroMIPSTb;
 
architecture Test OF MicroMIPSTb is
 
  -- Declaraci�n del micro (sin memoria)
	component MicroMIPS
	port (
		Clk : in std_logic; -- Reloj
		NRst : in std_logic; -- Reset activo a nivel bajo
		MemProgAddr : out unsigned(31 downto 0); -- Direcci�n para la memoria de programa
		MemProgData : in unsigned(31 downto 0); -- C�digo de operaci�n
		MemDataAddr : out unsigned(31 downto 0); -- Direcci�n para la memoria de datos
		MemDataDataRead : in signed(31 downto 0); -- Dato a leer en la memoria de datos
		MemDataDataWrite : out signed(31 downto 0); -- Dato a guardar en la memoria de datos
		MemDataWE : out std_logic
	);
	end component;

	-- Declaraci�n de la memoria de c�digo/programa
	component MemProgMIPS
	port (
		MemProgAddr : in unsigned(31 downto 0); -- Direcci�n para la memoria de programa
		MemProgData : out unsigned(31 downto 0) -- C�digo de operaci�n
	);
	end component;
	
	-- Declaraci�n de la memoria de c�digo/programa
	component MemDataMIPS
	port (
		Clk : in std_logic;
		NRst : in std_logic;
		MemDataAddr : in unsigned(31 downto 0);
		MemDataDataWrite : in signed(31 downto 0);
		MemDataWE : in std_logic;
		MemDataDataRead : out signed(31 downto 0)
	);
	end component;

	-- Entradas al micro
	-- En los bancos de prueba se pueden usar valores iniciales en las
	-- declaraciones, pero en los m�dulos no porque no son sintetizables
	signal memProgData : unsigned(31 downto 0) := (others => '0');
	signal memDataDataRead : signed(31 downto 0) := (others => '0');
	signal nRst : std_logic := '0';
	signal clk : std_logic := '0';

	-- Salidas del micro
	signal memProgAddr, memDataAddr : unsigned(31 downto 0);
	signal memDataDataWrite : signed(31 downto 0) := (others => '0');
	signal memDataWE : std_logic := '0';

	-- Periodo de reloj
	constant CLKPERIOD : time := 10 ns;

	-- Fin simulaci�n, por si queremos matar la simulaci�n por falta de eventos
	signal finSimu : boolean := false;

begin
 
	-- Instancia del micro
	uut: MicroMIPS
	port map(
		Clk => Clk,
		NRst => NRst,
		MemProgAddr => memProgAddr,
		MemProgData => memProgData,
		MemDataAddr => memDataAddr,
		MemDataDataRead => memDataDataRead,
		MemDataDataWrite => memDataDataWrite,
		MemDataWE => memDataWE
	);

	-- Instancia de la memoria de c�digo/programa
	mprog: MemProgMIPS
	port map (
		MemProgAddr => memProgAddr,
		MemProgData => memProgData
	);

	-- Instancia de la memoria de datos
	mdata: MemDataMIPS
	port map (
		Clk => Clk,
		NRst => NRst,
		MemDataAddr => memDataAddr,
		MemDataDataRead => memDataDataRead,
		MemDataDataWrite => memDataDataWrite,
		MemDataWE => memDataWE
		
	);
	
	CLKPROCESS: process
	begin
	while (not finSimu) loop
		clk <= '0';
		wait for CLKPERIOD/2;
		clk <= '1';
		wait for CLKPERIOD/2;
	end loop;
	wait;
	end process;

	-- Proceso principal de activar se�ales.
	-- S�lo hay que activar el reset. El resto del banco de pruebas 
	-- se cambia a trav�s del valor inicial de las memorias, que
	-- constituyen el programa a ejecutar.
	StimProc: process
	begin
		nRST <= '0'; -- Reset empieza activo
		wait for CLKPERIOD*2;
		nRST <= '1'; -- Se desactiva el reset y empieza la ejecuci�n
		wait for CLKPERIOD*1000;
		finSimu <= true; -- Si la simulaci�n tiene m�s de 100 instrucciones
		-- habr�a que esperar m�s antes de matar la simulaci�n
		wait; -- No se vuelve a hacer nada con el reset
	end process;

end Test;
