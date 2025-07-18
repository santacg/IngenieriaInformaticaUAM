from django.urls import reverse_lazy
from django.views import generic
from .models import Switch, Sensor, Reloj, Regla, Evento
from django.shortcuts import render

# Create your views here.


def base(request):
    return render(request, 'base.html')


def lista_dispositivos(request):
    switches = Switch.objects.all()
    sensores = Sensor.objects.all()
    relojes = Reloj.objects.all()

    return render(request, 'dispositivos/lista_dispositivos.html', {
        'switches': switches,
        'sensores': sensores,
        'relojes': relojes,
    })


def lista_reglas(request):
    reglas = Regla.objects.all()

    return render(request, 'reglas/lista_reglas.html', {
        'reglas': reglas,
    })


def lista_eventos(request):
    eventos = Evento.objects.all()

    return render(request, 'eventos/lista_eventos.html', {
        'eventos': eventos,
    })


class SwitchCreateView(generic.CreateView):
    model = Switch
    fields = ['nombre', 'state']
    template_name = 'dispositivos/switch_create.html'
    success_url = reverse_lazy('lista_dispositivos')


class SwitchDetailView(generic.DetailView):
    model = Switch
    template_name = 'dispositivos/switch_detail.html'


class SwitchUpdateView(generic.UpdateView):
    model = Switch
    fields = ['nombre', 'state']
    template_name = 'dispositivos/switch_update.html'
    success_url = reverse_lazy('lista_dispositivos')


class SwitchDeleteView(generic.DeleteView):
    model = Switch
    template_name = 'dispositivos/switch_delete.html'
    success_url = reverse_lazy('lista_dispositivos')


class SensorCreateView(generic.CreateView):
    model = Sensor
    fields = ['nombre', 'valor', 'unidad_de_medida']
    template_name = 'dispositivos/sensor_create.html'
    success_url = reverse_lazy('lista_dispositivos')


class SensorUpdateView(generic.UpdateView):
    model = Sensor
    fields = ['nombre', 'valor', 'unidad_de_medida']
    template_name = 'dispositivos/sensor_update.html'
    success_url = reverse_lazy('lista_dispositivos')


class SensorDeleteView(generic.DeleteView):
    model = Sensor
    template_name = 'dispositivos/sensor_delete.html'
    success_url = reverse_lazy('lista_dispositivos')


class RelojCreateView(generic.CreateView):
    model = Reloj
    fields = ['nombre', 'tiempo']
    template_name = 'dispositivos/reloj_create.html'
    success_url = reverse_lazy('lista_dispositivos')


class RelojUpdateView(generic.UpdateView):
    model = Reloj
    fields = ['nombre', 'tiempo']
    template_name = 'dispositivos/reloj_update.html'
    success_url = reverse_lazy('lista_dispositivos')


class RelojDeleteView(generic.DeleteView):
    model = Reloj
    template_name = 'dispositivos/reloj_delete.html'
    success_url = reverse_lazy('lista_dispositivos')


class ReglaCreateView(generic.CreateView):
    model = Regla
    fields = ['nombre', 'sensor_asociado', 'condicion', 'accion', 'switch_asociado']
    template_name = 'reglas/regla_create.html'
    success_url = reverse_lazy('lista_reglas')

    def get_context_data(self, **kwargs):
        context = super(ReglaCreateView, self).get_context_data(**kwargs)
        context['sensores'] = Sensor.objects.all()
        context['switches'] = Switch.objects.all()
        return context


class ReglaUpdateView(generic.UpdateView):
    model = Regla
    fields = ['nombre', 'sensor_asociado', 'condicion', 'accion', 'switch_asociado']
    template_name = 'reglas/regla_update.html'
    success_url = reverse_lazy('lista_reglas')

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['sensores'] = Sensor.objects.all()
        context['switches'] = Switch.objects.all()
        return context


class ReglaDeleteView(generic.DeleteView):
    model = Regla
    template_name = 'reglas/regla_delete.html'
    success_url = reverse_lazy('lista_reglas')
