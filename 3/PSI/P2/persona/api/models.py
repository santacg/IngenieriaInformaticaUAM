from django.db import models
from django.urls import reverse

# Create your models here.
class Persona(models.Model):
    nombre = models.CharField(max_length=100)
    apellido = models.CharField(max_length=100)
    email = models.EmailField(unique=True)

    class Meta:
        ordering = ['id']

    def __str__(self):
        return self.nombre
    
    def get_absolute_url(self):
        return reverse('persona', args=[str(self.id)])
