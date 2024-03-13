#api/views.py
from .models import Persona
from .serializers import PersonaSerializer
from rest_framework import viewsets
from django.shortcuts import render

# Create your views here.
class PersonaViewSet(viewsets.ModelViewSet):
    queryset = Persona.objects.all()
    serializer_class = PersonaSerializer


