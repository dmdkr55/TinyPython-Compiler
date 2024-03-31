.class public Test
.super java/lang/Object
; standard initializer
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method
.method public static sum(II)I
.limit stack 32
.limit locals 32
iload_0
iload_1
iadd
istore_2
iload_2
ireturn
.end method
.method public static main([Ljava/lang/String;)V
.limit stack 32
.limit locals 32
ldc 3
istore_1
ldc 5
istore_2
iload_1
iload_2
invokestatic Test/sum(II)I
istore_3
getstatic java/lang/System/out Ljava/io/PrintStream;
iload_3
invokevirtual java/io/PrintStream/println(I)V
iload_3
ldc 100
if_icmple elseLabel1
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "dw"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto endLabel1
elseLabel1:
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "sdw"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
endLabel1:
return
.end method
