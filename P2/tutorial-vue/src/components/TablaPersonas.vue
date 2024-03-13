<template>
  <div id="tabla-personas">
    <div
      v-if="!personas.length"
      class="alert alert-info"
      role="alert"
    >
      No se han encontrado personas
    </div>
    <div v-else>
      <table class="table">
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Email</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="persona in personas "
            :key="persona.id"
          >
            <td v-if="editando === persona.id">
              <input
                id="persona.nombre"
                v-model="persona.nombre"
                type="text"
                class="form-control"
                data-cy="persona-nombre"
              >
            </td>
            <td v-else>
              {{ persona.nombre }}
            </td>
            <td v-if="editando === persona.id">
              <input
                v-model="persona.apellido"
                type="text"
                class="form-control"
              >
            </td>
            <td v-else>
              {{ persona.apellido }}
            </td>
            <td v-if="editando === persona.id">
              <input
                v-model="persona.email"
                type="email"
                class="form-control"
              >
            </td>
            <td v-else>
              {{ persona.email }}
            </td>
            <td>
              <!-- Botones de edición, guardar y cancelar -->
            </td>
            <td v-if="editando === persona.id">
              <button
                class="btn btn-success"
                data-cy="save-button"
                @click="guardarPersona(persona)"
              >
                &#x1F5AB; Guardar
              </button>
              <button
                class="btn btn-secondary ml-2"
                data-cy="cancel-button"
                @click="cancelarEdicion(persona)"
              >
                &#x1F5D9; Cancelar
              </button>
            </td>
            <td v-else>
              <button
                class="btn btn-info"
                data-cy="edit-button"
                @click="editarPersona(persona)"
              >
                &#x1F58A; Editar
              </button>
              <button
                class="btn btn-danger ml-2"
                @click="$emit('delete-persona', persona.id)"
              >
                &#x1F5D1; Eliminar
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
// Definicion del componente Vue
export default {
  // Nombre del componente, en este caso 'tabla-personas'
  name: "TablaPersonas",

  // Propiedades que puede recibir el componente
  props: {
    // La propiedad 'personas' se espera que sea un array
    personas: {
      type: Array,
      default: () => [] // Array vacío por defecto
    },
  },
  emits: ['delete-persona'],
  setup(props, ctx) {
    const editando = ref(null);
    const personaEditada = ref(null);
    const editarPersona = (persona) => {
      personaEditada.value = { ...persona };
      editando.value = persona.id;
    };
    return {
      editando,
      editarPersona
    };
  }
};
</script>
<style scoped>
/* Estilos especificos del componente con el modificador scoped */
</style>
