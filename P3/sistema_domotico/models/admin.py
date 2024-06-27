from django.contrib import admin
from .models import Switch, Sensor, Reloj, Regla, Evento

# Register your models here.


class SwitchAdmin(admin.ModelAdmin):
    list_display = ('nombre', 'topic', 'state')
    list_filter = ('state',)


class SensorAdmin(admin.ModelAdmin):
    list_display = ('nombre', 'topic', 'unidad_de_medida')
    list_filter = ('unidad_de_medida',)


class RelojAdmin(admin.ModelAdmin):
    list_display = ('nombre', 'topic')


class ReglaAdmin(admin.ModelAdmin):
    list_display = ('nombre', 'sensor_asociado',
                    'condicion', 'switch_asociado', 'accion')
    search_fields = ('nombre', 'sensor_asociado',
                     'condicion', 'switch_asociado', 'accion')


admin.site.register(Switch, SwitchAdmin)
admin.site.register(Sensor, SensorAdmin)
admin.site.register(Reloj, RelojAdmin)
admin.site.register(Regla, ReglaAdmin)
