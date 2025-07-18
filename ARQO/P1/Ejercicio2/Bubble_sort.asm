.data
    array: .word 5, 2, 9, 1, 5, 6, 8, 3, 7, 4
    sortedArray: .space 40
.text 

    la t1, array          	# Initialize pointers for copying
    la t2, sortedArray
    li t4, 10             	# There are 10 elements in the array

copy_loop:
    lw t3, (t1)    
    sw t3, (t2)
    addi t1, t1, 4
    addi t2, t2, 4
    addi t4, t4, -1
    bnez t4, copy_loop

    li      t4, 10        	# There are 10 elements in the array

bubblesort:
    xor	t2, t2, t2		# changed flag = 0
    la 	t3, sortedArray		# stash current pointet
    addi    t4, t4, -1         # Decrease the number of pairs to check by 1
    mv      t1, t4             # Copy the value to t1

loop:	
    lw 	t5, 0(t3)
    lw 	t6, 4(t3)
    beqz t1, loop_exit
    slt a0, t6, t5		# a0 = 0 if t5 > t6
    beqz a0, loop_tail 		# Loop if there is no need to swap (a0 = 0)
    sw 	t6, 0(t3) 	       	# Swap
    sw 	t5, 4(t3)
    ori	t2, t2, 1 		# changed flag = 1
				
loop_tail:	
    addi t1, t1, -1
    addi t3, t3, 4
    bnez t1, loop             	# Jump if t1 is not zero

loop_exit:	
    bnez t2, bubblesort	     	# If swap was done, loop again

end:	
    ret