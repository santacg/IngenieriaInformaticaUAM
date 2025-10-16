	.file	"simple2.c"
	.text
	.section	.text.startup,"ax",@progbits
	.p2align 4
	.globl	main
	.type	main, @function
main:
.LFB39:
	.cfi_startproc
	endbr64
	leaq	b(%rip), %rdx
	leaq	a(%rip), %rcx
	vmovdqa	.LC0(%rip), %ymm1
	vpbroadcastd	.LC4(%rip), %ymm4
	vpbroadcastd	.LC5(%rip), %ymm3
	movq	%rdx, %rax
	movq	%rcx, %rsi
	leaq	16384(%rdx), %rdi
.L2:
	vmovdqa	%ymm1, %ymm0
	vcvtdq2pd	%xmm0, %ymm2
	vmovapd	%ymm2, (%rax)
	vextracti128	$0x1, %ymm0, %xmm2
	vpaddd	%ymm3, %ymm0, %ymm0
	vcvtdq2pd	%xmm2, %ymm2
	addq	$64, %rax
	vmovapd	%ymm2, -32(%rax)
	vcvtdq2pd	%xmm0, %ymm2
	vextracti128	$0x1, %ymm0, %xmm0
	vmovapd	%ymm2, (%rsi)
	vcvtdq2pd	%xmm0, %ymm0
	vpaddd	%ymm4, %ymm1, %ymm1
	vmovapd	%ymm0, 32(%rsi)
	addq	$64, %rsi
	cmpq	%rdi, %rax
	jne	.L2
	vmovsd	c(%rip), %xmm3
	vbroadcastsd	.LC6(%rip), %ymm4
	movl	$1000000, %esi
.L3:
	xorl	%eax, %eax
	.p2align 4,,10
	.p2align 3
.L4:
	vmovapd	(%rcx,%rax), %ymm1
	vfmadd213pd	(%rdx,%rax), %ymm4, %ymm1
	addq	$32, %rax
	vaddsd	%xmm3, %xmm1, %xmm2
	vunpckhpd	%xmm1, %xmm1, %xmm0
	vextractf64x2	$0x1, %ymm1, %xmm1
	vaddsd	%xmm2, %xmm0, %xmm0
	vaddsd	%xmm1, %xmm0, %xmm0
	vunpckhpd	%xmm1, %xmm1, %xmm1
	vaddsd	%xmm1, %xmm0, %xmm3
	cmpq	$16384, %rax
	jne	.L4
	decl	%esi
	jne	.L3
	xorl	%eax, %eax
	vmovsd	%xmm3, c(%rip)
	vzeroupper
	ret
	.cfi_endproc
.LFE39:
	.size	main, .-main
	.local	c
	.comm	c,8,8
	.local	b
	.comm	b,16384,32
	.local	a
	.comm	a,16384,32
	.section	.rodata.cst32,"aM",@progbits,32
	.align 32
.LC0:
	.long	0
	.long	1
	.long	2
	.long	3
	.long	4
	.long	5
	.long	6
	.long	7
	.section	.rodata.cst4,"aM",@progbits,4
	.align 4
.LC4:
	.long	8
	.set	.LC5,.LC0+4
	.section	.rodata.cst8,"aM",@progbits,8
	.align 8
.LC6:
	.long	-611603343
	.long	1072693352
	.ident	"GCC: (Ubuntu 11.4.0-1ubuntu1~22.04) 11.4.0"
	.section	.note.GNU-stack,"",@progbits
	.section	.note.gnu.property,"a"
	.align 8
	.long	1f - 0f
	.long	4f - 1f
	.long	5
0:
	.string	"GNU"
1:
	.align 8
	.long	0xc0000002
	.long	3f - 2f
2:
	.long	0x3
3:
	.align 8
4:
