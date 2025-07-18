##########################################################################
#   Programa de prueba para Practica 1 ARQO 2023                         #
#                                                                        #
##########################################################################
# Programa en ensamblador RISC-V para probar el funcionamiento de la P1. 
# Incluye todas las instrucciones a verificar. Los saltos de los beq se 
# realizan de forma efectiva si las operaciones anteriores han devuelto
# resultados correctos en los registros. 
# El programa termina con un buble infinito (En RARS mejor ver paso a paso)
# En las formas de ondas debes analizar el funcionamiento
#
############################################################################
.data
	num0:   .word  5 # posic base + 0
.text 

main:		lui  t0, %hi(num0)
		addi   t3, zero, 5
		addi   t2, zero, 2
		addi   t1, zero, 1
		nop
		nop
		nop

Pru_Flush:		beq t1, t1, Flush
			add t3, t3, t3
			add t2, t2, t2
			add t1, t1, t1
Flush:			bne t1, t1, ERROR
			bne t2, t2, ERROR
			bne t3, t3, ERROR

Pru_InsR_Efectiva:	add a0, t1, t1
			beq  a0, t2, Pru_InsR_NoEfectiva
			j ERROR

Pru_InsR_NoEfectiva:	add a1, t2, t2
			beq a1, t1, ERROR
			
Pru_InsMEM_Efectiva:	lw a2, (t0)
			beq  a2, t3, Pru_InsMEM_NoEfectiva
			j ERROR

Pru_InsMEM_NoEfectiva:	lw a3, (t0)
			beq a3, t1, ERROR
			j OK_prog

ERROR: 	       nop
	       nop
	       nop
	       addi t6, t6, -1  # decrementa infinitamente t5
               beq  x0, x0, ERROR
               nop
               nop
               nop
               
OK_prog: addi t6, t6, 1      # incrementa infinitamente t6 (x31)
         beq  x0, x0, OK_prog
         nop
         nop
         nop