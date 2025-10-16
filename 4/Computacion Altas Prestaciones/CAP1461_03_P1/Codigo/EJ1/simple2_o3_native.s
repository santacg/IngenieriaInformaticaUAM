	.file	"simple2.c"
	.text
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC1:
	.string	"%lf"
	.section	.text.startup,"ax",@progbits
	.p2align 4
	.globl	main
	.type	main, @function
main:
.LFB39:
	.cfi_startproc
	endbr64
	subq	$8, %rsp
	.cfi_def_cfa_offset 16
	xorl	%eax, %eax
	leaq	b(%rip), %rcx
	leaq	a(%rip), %rdx
	.p2align 4,,10
	.p2align 3
.L2:
	pxor	%xmm0, %xmm0
	leal	1(%rax), %esi
	cvtsi2sdl	%eax, %xmm0
	movsd	%xmm0, (%rcx,%rax,8)
	pxor	%xmm0, %xmm0
	cvtsi2sdl	%esi, %xmm0
	movsd	%xmm0, (%rdx,%rax,8)
	addq	$1, %rax
	cmpq	$2048, %rax
	jne	.L2
	movsd	c(%rip), %xmm1
	movsd	.LC0(%rip), %xmm2
	xorl	%eax, %eax
	.p2align 4,,10
	.p2align 3
.L3:
	movsd	(%rdx,%rax), %xmm0
	mulsd	%xmm2, %xmm0
	addsd	(%rcx,%rax), %xmm0
	addq	$8, %rax
	addsd	%xmm0, %xmm1
	cmpq	$16384, %rax
	jne	.L3
	movapd	%xmm1, %xmm0
	movl	$1, %edi
	leaq	.LC1(%rip), %rsi
	movl	$1, %eax
	movsd	%xmm1, c(%rip)
	call	__printf_chk@PLT
	xorl	%eax, %eax
	addq	$8, %rsp
	.cfi_def_cfa_offset 8
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
	.section	.rodata.cst8,"aM",@progbits,8
	.align 8
.LC0:
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
