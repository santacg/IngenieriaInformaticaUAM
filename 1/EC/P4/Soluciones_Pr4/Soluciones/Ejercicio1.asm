######################################################################
## Fichero: ProgramaMips.asm
## Descripción: Programa de prueba para el Micro MIPS
## Asignatura: E.C. 1º grado
## Grupo de Prácticas: 
## Grupo de Teoría: 
## Práctica: 4
## Ejercicio: 1
######################################################################

.text # Comienzo de seccion de codigo de usuario
main:	addi $1, $0, 4 # Carga 4 en $1
	ori $2, $0, 15 # Carga x0F en $2
	andi $3, $2, 4 # Carga 4 en $3
	addi $4, $3, -20 # Carga -16 (0xFFFFFFF0) guarda en $4
	slti $5, $0, 0x7FFF # Carga 1 en $5
	slti $5, $0, -1 # Carga 0 en $5
	slt $6, $4, $1 # Carga 1 en $6
	
	lw $7, datoA # Carga datoA en $7. Carga 10
	lw $8, datoA($1) # Carga datoA+4 (datoB) en $8. Carga 9
	lw $9, datoC # Carga datoC en $9. Carga 9
	sub $10, $8, $7 # $10=$8-$7. Carga -1
	
	beq $1, $4, nosalta
	beq $8, $9, salta

nosalta: add $10, $0, $0 # No se debe ejecutar (pondría un 0 en $10)
salta:	addi $11, $10, 2 # Suma con inmediato negativo. Carga 1 en $11
	add $12, $0, $1 # Carga 4 en $12
	sw $12, resultado # Guarda $12 en resultado. Guarda 4
	lw $13, resultado # Carga resultado en $13. Carga 4
	nor $14, $1, $1 # Carga 0xFFFFFFFB en $14
	xor $2, $14, $14 # Carga 0x00000000 en $2
	and $14, $14, $0 # Carga 0 en $14
	or $15, $1, $2 # Carga 0x00000004 en $15
	xori $16, $15, 0x13 # Carga 0x0000001C en $16
fin:	j fin	
	

.data
datoA:  10
datoB:  9 
datoC:  9 
resultado: .space 4
