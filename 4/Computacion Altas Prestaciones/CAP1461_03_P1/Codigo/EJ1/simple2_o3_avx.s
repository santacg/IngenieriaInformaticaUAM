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
	vmovdqa	.LC0(%rip), %xmm2
	leaq	a(%rip), %rcx
	vmovdqa	.LC1(%rip), %xmm4
	movq	%rdx, %rax
	vmovdqa	.LC2(%rip), %xmm3
	movq	%rcx, %rsi
	leaq	16384(%rdx), %rdi
.L2:
	vmovdqa	%xmm2, %xmm0
	addq	$32, %rax
	vpaddd	%xmm4, %xmm2, %xmm2
	vcvtdq2pd	%xmm0, %xmm1
	vmovapd	%xmm1, -32(%rax)
	vpshufd	$238, %xmm0, %xmm1
	vpaddd	%xmm3, %xmm0, %xmm0
	vcvtdq2pd	%xmm1, %xmm1
	addq	$32, %rsi
	vmovapd	%xmm1, -16(%rax)
	vcvtdq2pd	%xmm0, %xmm1
	vpshufd	$238, %xmm0, %xmm0
	vmovapd	%xmm1, -32(%rsi)
	vcvtdq2pd	%xmm0, %xmm0
	vmovapd	%xmm0, -16(%rsi)
	cmpq	%rdi, %rax
	jne	.L2
	vmovsd	c(%rip), %xmm3
	movl	$1000000, %esi
	vmovapd	.LC3(%rip), %ymm4
.L3:
	xorl	%eax, %eax
	.p2align 4,,10
	.p2align 3
.L4:
	vmulpd	(%rcx,%rax), %ymm4, %ymm0
	vaddpd	(%rdx,%rax), %ymm0, %ymm0
	addq	$32, %rax
	vaddsd	%xmm3, %xmm0, %xmm2
	vunpckhpd	%xmm0, %xmm0, %xmm1
	vextractf128	$0x1, %ymm0, %xmm0
	vaddsd	%xmm2, %xmm1, %xmm1
	vaddsd	%xmm0, %xmm1, %xmm1
	vunpckhpd	%xmm0, %xmm0, %xmm0
	vaddsd	%xmm0, %xmm1, %xmm3
	cmpq	$16384, %rax
	jne	.L4
	subl	$1, %esi
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
	.section	.rodata.cst16,"aM",@progbits,16
	.align 16
.LC0:
	.long	0
	.long	1
	.long	2
	.long	3
	.align 16
.LC1:
	.long	4
	.long	4
	.long	4
	.long	4
	.align 16
.LC2:
	.long	1
	.long	1
	.long	1
	.long	1
	.section	.rodata.cst32,"aM",@progbits,32
	.align 32
.LC3:
	.long	-611603343
	.long	1072693352
	.long	-611603343
	.long	1072693352
	.long	-611603343
	.long	1072693352
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
