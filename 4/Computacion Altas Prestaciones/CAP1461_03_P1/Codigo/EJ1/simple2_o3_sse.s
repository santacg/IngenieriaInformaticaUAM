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
	leaq	b(%rip), %rcx
	leaq	a(%rip), %rdx
	xorl	%edi, %edi
	movdqa	.LC0(%rip), %xmm1
	leaq	16320(%rcx), %r8
	movq	%rdx, %rsi
	movq	%rcx, %rax
	movdqa	.LC1(%rip), %xmm3
	movdqa	.LC2(%rip), %xmm4
	movdqa	.LC3(%rip), %xmm6
	movdqa	.LC4(%rip), %xmm5
.L2:
	cvtdq2pd	%xmm1, %xmm0
	movdqa	%xmm1, %xmm2
	prefetcht0	400(%rax)
	movaps	%xmm0, (%rax)
	prefetcht0	400(%rsi)
	pshufd	$238, %xmm1, %xmm0
	paddd	%xmm3, %xmm2
	addq	$64, %rax
	addq	$64, %rsi
	addl	$2, %edi
	cvtdq2pd	%xmm0, %xmm0
	movaps	%xmm0, -48(%rax)
	movdqa	%xmm1, %xmm0
	paddd	%xmm4, %xmm0
	cvtdq2pd	%xmm0, %xmm7
	pshufd	$238, %xmm0, %xmm0
	movaps	%xmm7, -64(%rsi)
	cvtdq2pd	%xmm2, %xmm7
	pshufd	$238, %xmm2, %xmm2
	cvtdq2pd	%xmm0, %xmm0
	movaps	%xmm0, -48(%rsi)
	movdqa	%xmm1, %xmm0
	paddd	%xmm6, %xmm1
	cvtdq2pd	%xmm2, %xmm2
	movaps	%xmm2, -16(%rax)
	paddd	%xmm5, %xmm0
	cvtdq2pd	%xmm0, %xmm2
	pshufd	$238, %xmm0, %xmm0
	movaps	%xmm7, -32(%rax)
	cvtdq2pd	%xmm0, %xmm0
	movaps	%xmm2, -32(%rsi)
	movaps	%xmm0, -16(%rsi)
	cmpq	%r8, %rax
	jne	.L2
	leaq	16336+b(%rip), %r9
	xorl	%eax, %eax
.L3:
	movdqa	%xmm1, %xmm0
	incl	%edi
	paddd	%xmm3, %xmm1
	cvtdq2pd	%xmm0, %xmm2
	movaps	%xmm2, (%r8,%rax)
	pshufd	$238, %xmm0, %xmm2
	paddd	%xmm4, %xmm0
	cvtdq2pd	%xmm2, %xmm2
	movaps	%xmm2, (%r9,%rax)
	cvtdq2pd	%xmm0, %xmm2
	pshufd	$238, %xmm0, %xmm0
	movaps	%xmm2, (%rsi,%rax)
	cvtdq2pd	%xmm0, %xmm0
	movaps	%xmm0, 16(%rsi,%rax)
	addq	$32, %rax
	cmpl	$511, %edi
	jbe	.L3
	movlpd	c(%rip), %xmm1
	movl	$1000000, %r8d
	movlpd	.LC5(%rip), %xmm2
.L4:
	leaq	184+a(%rip), %rsi
	leaq	184+b(%rip), %rax
	xorl	%edi, %edi
.L7:
	movlpd	-184(%rsi), %xmm0
	addl	$8, %edi
	prefetcht0	(%rsi)
	prefetcht0	(%rax)
	addq	$64, %rsi
	addq	$64, %rax
	mulsd	%xmm2, %xmm0
	addsd	-248(%rax), %xmm0
	addsd	%xmm1, %xmm0
	movlpd	-240(%rsi), %xmm1
	mulsd	%xmm2, %xmm1
	addsd	-240(%rax), %xmm1
	addsd	%xmm1, %xmm0
	movlpd	-232(%rsi), %xmm1
	mulsd	%xmm2, %xmm1
	addsd	-232(%rax), %xmm1
	addsd	%xmm1, %xmm0
	movlpd	-224(%rsi), %xmm1
	mulsd	%xmm2, %xmm1
	addsd	-224(%rax), %xmm1
	addsd	%xmm1, %xmm0
	movlpd	-216(%rsi), %xmm1
	mulsd	%xmm2, %xmm1
	addsd	-216(%rax), %xmm1
	addsd	%xmm1, %xmm0
	movlpd	-208(%rsi), %xmm1
	mulsd	%xmm2, %xmm1
	addsd	-208(%rax), %xmm1
	addsd	%xmm1, %xmm0
	movlpd	-200(%rsi), %xmm1
	mulsd	%xmm2, %xmm1
	addsd	-200(%rax), %xmm1
	addsd	%xmm1, %xmm0
	movlpd	-192(%rsi), %xmm1
	mulsd	%xmm2, %xmm1
	addsd	-192(%rax), %xmm1
	cmpl	$2040, %edi
	addsd	%xmm0, %xmm1
	jne	.L7
	movl	$2040, %eax
	.p2align 4,,7
	.p2align 3
.L5:
	movlpd	(%rdx,%rax,8), %xmm0
	mulsd	%xmm2, %xmm0
	addsd	(%rcx,%rax,8), %xmm0
	incq	%rax
	cmpq	$2048, %rax
	addsd	%xmm0, %xmm1
	jne	.L5
	decl	%r8d
	jne	.L4
	movsd	%xmm1, c(%rip)
	xorl	%eax, %eax
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
	.align 16
.LC3:
	.long	8
	.long	8
	.long	8
	.long	8
	.align 16
.LC4:
	.long	5
	.long	5
	.long	5
	.long	5
	.section	.rodata.cst8,"aM",@progbits,8
	.align 8
.LC5:
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
