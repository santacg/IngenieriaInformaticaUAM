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
	num0:   .word  10 # posic base + 0
.text 

main:		addi   t3, zero, 10
		addi   t6, zero, 6
		addi   t4, zero, 4
		addi   t2, zero, 2
		addi   t1, zero, 1
		nop
		nop
		nop
		
# 00 con 00	
Pru_notForwardAB:	add  a0, t1, t1
			add  a0, t1, t1
			add  a0, t1, t1
			nop
			nop
			nop 
			bne  a0, t2, ERROR_FORWARD
# 00 con 10						
Pru_notForwardA_ForwardB_MEM: add a1, t1, t1
			      add a1, t2, a1
			      nop
			      nop
			      nop
			      bne a1, t4, ERROR_FORWARD
# 00 con 01			      
Pru_notForwardA_ForwardB_WB:  add a1, t1, t1
			      nop
			      add a1, t2, a1
			      nop
			      nop
			      nop
			      bne a1, t4, ERROR_FORWARD
# 10 con 00
Pru_ForwardA_MEM_notForwardB: add a1, t1, t1
			      add a1, a1, t2
			      nop
			      nop
			      nop
			      bne a1, t4, ERROR_FORWARD
# 10 con 10
Pru_ForwardA_MEM_ForwardB_MEM: add a1, t1, t1 
			       add a1, a1, a1
			       nop 
			       nop
			       nop
			       bne a1, t4, ERROR_FORWARD
# 10 con 01  			      
Pru_ForwardA_MEM_ForwardB_WB:  add a1, t1, t1
			       add a2, t1, t1
			       add a1, a2, a1
			       nop
			       nop
			       nop
			       nop
			       bne a1, t4, ERROR_FORWARD

# 01 con 00 	  
Pru_ForwardA_WB_notForwardB:  add a1, t1, t1
			      nop
			      add a1, a1, t2
			      nop
			      nop
			      nop
			      bne a1, t4, ERROR_FORWARD

# 01 con 10 	  
Pru_ForwardA_WB_ForwardB_MEM: add a1, t1, t1
			      add a2, t1, t1
			      add a1, a1, a2
			      nop
			      nop
			      nop
			      bne a1, t4, ERROR_FORWARD
			 
# 01 con 01 	  
Pru_ForwardA_WB_ForwardB_WB: add a1, t1, t1
			     nop
			     add a1, a1, a1
			     nop
			     nop
			     nop
			     bne a1, t4, ERROR_FORWARD

Pru_RegBank_Internal_Forwarding: add a1, t1, t1
				 nop
				 nop
				 add a1, a1, a1 # a1 valia 4 pero se sobreescribe por add a1, t1, t1 que es 2 que esta en WB
				 bne a1, t4, ERROR_REGBANK		     

Pru_HazardLW: li t3, 20
	      lui  t0, %hi(num0)
	      nop
	      nop
	      nop 
	      lw s0, 0(t0)
	      add s1, s0, s0
	      bne s1, t3, ERROR_HAZARDLW
	      
J_OKPROG: jal OK_prog
          nop
          nop
          nop
#--- Buclwa errores y final de prog 
ERROR_FORWARD: addi t6, t6, -1  # decrementa infinitamente t5
               beq  x0, x0, ERROR_FORWARD
               nop
               nop
               nop

ERROR_REGBANK: addi t4, t4, -1  # decrementa infinitamente t5
               beq  x0, x0, ERROR_REGBANK
               nop
               nop
               nop

ERROR_HAZARDLW: addi t3, t3, -1  # decrementa infinitamente t5
                beq  x0, x0, ERROR_HAZARDLW
                nop
                nop
                nop
                
OK_prog: addi t6, t6, 1      # incrementa infinitamente t6 (x31)
         beq  x0, x0, OK_prog
         nop
         nop
         nop
