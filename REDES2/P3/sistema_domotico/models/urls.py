from django.urls import path
from . import views

urlpatterns = [
    path('', views.base, name='base'),
    path('dispositivos/', views.lista_dispositivos, name='lista_dispositivos'),
    path('switch/create/', views.SwitchCreateView.as_view(), name='switch_create'),
    path('switch/<int:pk>/update/', views.SwitchUpdateView.as_view(), name='switch_update'),
    path('switch/<int:pk>/delete/', views.SwitchDeleteView.as_view(), name='switch_delete'),
    path('sensor/create/', views.SensorCreateView.as_view(), name='sensor_create'),
    path('sensor/<int:pk>/update/', views.SensorUpdateView.as_view(), name='sensor_update'),
    path('sensor/<int:pk>/delete/', views.SensorDeleteView.as_view(), name='sensor_delete'),
    path('reloj/create/', views.RelojCreateView.as_view(), name='reloj_create'),
    path('reloj/<int:pk>/update/', views.RelojUpdateView.as_view(), name='reloj_update'),
    path('reloj/<int:pk>/delete/', views.RelojDeleteView.as_view(), name='reloj_delete'),
    path('reglas/', views.lista_reglas, name='lista_reglas'),
    path('regla/create/', views.ReglaCreateView.as_view(), name='regla_create'),
    path('regla/<int:pk>/update/', views.ReglaUpdateView.as_view(), name='regla_update'),
    path('regla/<int:pk>/delete/', views.ReglaDeleteView.as_view(), name='regla_delete'),
    path('eventos/', views.lista_eventos, name='lista_eventos')
]
