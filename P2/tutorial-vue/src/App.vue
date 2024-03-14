<template>
  <div id="app" class="container">
    <div class="row">
      <div class="col-md-12">
        <h1>Personas</h1>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <formulario-persona @add-persona="agregarPersona" />
        <tabla-personas :personas="personas" @delete-persona="eliminarPersona"
          @actualizar-persona="actualizarPersona" />
      </div>
    </div>
  </div>
  <div>
    <p>Count is {{ store.count }}</p>
  </div>
</template>
<script>

import TablaPersonas from "@/components/TablaPersonas.vue";
import FormularioPersona from "@/components/FormularioPersona.vue";
import { useCounterStore } from '@/stores/counter';
import { ref, onMounted } from "vue";

const myVar = import.meta.env.VITE_DJANGOURL;
// Exportacion del componente principal
export default {
  // Nombre del componente principal
  name: "App",
  // Registro del componente "TablaPersonas" para su uso en este componente
  components: {
    TablaPersonas,
    FormularioPersona,
  },
  // Configuracion del componente usando el nuevo sistema "setup"
  setup() {
    // Declaracion de una variable reactiva "personas" usando "ref"
    const personas = ref([]);
    const store = useCounterStore();
    const listadoPersonas = async () => {
      // Metodo para obtener un listado de personas
      try {
        const response = await fetch('http://127.0.0.1:8001/api/v1/');
        personas.value = await response.json();
      } catch (error) {
        console.error(error);
      }
    };
    const agregarPersona = async (persona) => {
      try {
        const response = await fetch('http://127.0.0.1:8001/api/v1/', {
          method: 'POST',
          body: JSON.stringify(persona),
          headers: { 'Content-type': 'application/json; charset=UTF-8' },
        });
        const personaCreada = await response.json();
        personas.value = [...personas.value, personaCreada];
        store.increment();
      } catch (error) {
        console.error(error);
      }
    };
    const actualizarPersona = async (id, personaActualizada) => {
      // Metodo para actualizar una persona
      try {
        const response = await fetch('https://my-json-server.tcom/rmarabini/people/personas/' + personaActualizada.id + '/', {
          method: 'PUT',
          body: JSON.stringify(personaActualizada),
          headers: { 'Content-type': 'application/json; charset=UTF -8' },
        });

        const personaActualizadaJS = await response.json();
        personas.value = personas.value.map(u => (u.id === personaActualizada.id ? personaActualizadaJS : u));
      } catch (error) {
        console.error(error);
      }
    };
    const eliminarPersona = async (persona_id) => {
      // Metodo para eliminar una persona
      try {
        await fetch('http://127.0.0.1:8001/api/v1/' + persona_id + '/', {
          method: "DELETE"
        });
        personas.value = personas.value.filter(u => u.id !== persona_id);
      } catch (error) {
        console.error(error);
      }
    };
    onMounted(() => {
      listadoPersonas();
    });
    // Retorno de las variables o funciones que el componente principal va a exponer
    return {
      personas,
      agregarPersona,
      eliminarPersona,
      actualizarPersona,
      store,
    };
  },
};
</script>

<style>
/* Estilos globales para todos los elementos button en la aplicacion
↪→ */
button {
  background: #009435;
  border: 1px solid #009435;
}
</style>
