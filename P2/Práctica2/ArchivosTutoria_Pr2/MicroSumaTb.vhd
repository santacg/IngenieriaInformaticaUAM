----------------------------------------------------------------------
----------------------------------------------------------------------
-- Asignatura: Estructura de Computadores. GII. 1er curso.
-- Fichero: MicroSuma.vhd
-- Descripción: Micro MIPS muy simplificado, sólo suma con dato inmediato
-- Fichero de apoyo para: Práctica: 2. Ejercicio: 3
----------------------------------------------------------------------
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity MicroSumaTb is
end MicroSumaTb;
 
architecture Test OF MicroSumaTb is
 
  -- Declaración del micro (sin memoria)
	component MicroSuma
	port (
		Clk : in std_logic; -- Reloj
		NRst : in std_logic; -- Reset activo a nivel bajo
		MemProgAddr : out unsigned(31 downto 0); -- Dirección para la memoria de programa
		MemProgData : in unsigned(31 downto 0) -- Código de operación
	);
	end component;

	-- Declaración de la memoria de código/programa
	component MemProgSuma
	port (
		MemProgAddr : in unsigned(31 downto 0); -- Dirección para la memoria de programa
		MemProgData : out unsigned(31 downto 0) -- Código de operación
	);
	end component;

	-- Entradas al micro
	-- En los bancos de prueba se pueden usar valores iniciales en las
	-- declaraciones, pero en los módulos no porque no son sintetizables
	signal memProgData : unsigned(31 downto 0);
	signal nRst : std_logic := '0';
	signal clk : std_logic := '0';

	-- Salidas del micro
	signal memProgAddr : unsigned(31 downto 0);

	-- Periodo de reloj
	constant CLKPERIOD : time := 10 ns;

	-- Fin simulación, por si queremos matar la simulación por falta de eventos
	signal finSimu : boolean := false;

begin
 
	-- Instancia del micro
	uut: MicroSuma
	port map(
		CLK => clk,
		nRST => nRST,
		MemProgAddr => memProgAddr,
		MemProgData => memProgData
	);

	-- Instancia de la memoria de código/programa
	mprog: MemProgSuma
	port map (
		MemProgAddr => memProgAddr,
		MemProgData => memProgData
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

	-- Proceso principal de activar señales.
	-- Sólo hay que activar el reset. El resto del banco de pruebas
	-- se cambia a través del valor inicial de las memorias, que
	-- constituyen el programa a ejecutar.
	StimProc: process
	begin
		nRST <= '0'; -- Reset empieza activo
		wait for CLKPERIOD*2;
		nRST <= '1'; -- Se desactiva el reset y empieza la ejecución
		wait for CLKPERIOD*10;
		finSimu <= true; -- Si la simulación tiene más de 10 instrucciones
		-- habría que esperar más antes de matar la simulación
		wait; -- No se vuelve a hacer nada con el reset
	end process;

end Test;
