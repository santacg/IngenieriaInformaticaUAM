# Criptografia
- Para todas las ejecuciones que vayan a cifrar, se ha implementado un procesador sencillo que transforma todo el texto a caracteres en mayuscula de la 'A' a la 'Z', eliminando espacios, saltos 
y caracteres en miniscula, aún así los algoritmos comprueban que los caracteres leidos esten dentro de dicha restricción
- Kasiski es computacionalmente muy costoso, además se ha implementado con GLIB2 para el uso de hashes, no está fuera de lo normal que tarde ~1 minuto en ejecutarse para textos grandes, ya que 
obtiene todos los n-gramas posibles del tamaño deseado del texto. Otro detalle es que al usar GLIB2 hay algunas fugas de memoria que no provocan errores, es decir se dejan punteros de los 
cuales todavía se tiene la referencia.
- Para el cifrado afin se puede emplear la entrada y salida estandar 
