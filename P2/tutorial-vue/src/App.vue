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
</template>
<script>
// Importacion del componente 'TablaPersonas' y el metodo 'ref' de Vue 3
import TablaPersonas from "@/components/TablaPersonas.vue";
import FormularioPersona from "@/components/FormularioPersona.vue";

import { ref } from "vue";
// Exportacion del componente principal
export default {
  // Nombre del componente principal
  name: "app",
  // Registro del componente "TablaPersonas" para su uso en este componente
  components: {
    TablaPersonas,
    FormularioPersona,
  },
  // Configuracion del componente usando el nuevo sistema "setup"
  setup() {
    // Declaracion de una variable reactiva "personas" usando "ref"
    const personas = ref([
      {
        id: 1,
        nombre: "Jon",
        apellido: "Nieve",
        email: "jon@email.com",
      },
      {
        id: 2,
        nombre: "Tyrion",
        apellido: "Lannister",
        email: "tyrion@email.com",
      },
      {
        id: 3,
        nombre: "Daenerys",
        apellido: "Targaryen",
        email: "daenerys@email.com",
      },
    ]);

    const agregarPersona = (persona) => {
      let id = 0;
      if (personas.value.length > 0) {
        id = personas.value[personas.value.length - 1].id + 1;
      }
      personas.value = [...personas.value, { ...persona, id }];
    };
    const eliminarPersona = (id) => {
      try {
        personas.value = personas.value.filter(
          u => u.id !== id
        );
      }
      catch (error) {
        console.error(error);
      }
    };
    const actualizarPersona = (id, personaActualizada) => {
      try {
        personas.value = personas.value.map(persona =>
          persona.id === id ? personaActualizada : persona);
      }
      catch (error) {
        console.error(error);
      }
    }
    // Retorno de las variables o funciones que el componente principal va a exponer
    return {
      personas,
      agregarPersona,
      eliminarPersona,
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
