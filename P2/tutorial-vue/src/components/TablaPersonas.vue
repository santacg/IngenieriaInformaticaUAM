<template>
  <div id="tabla-personas">
    <div v-if="!personas.length" class="alert alert-info" role="alert">
      No se han agregado personas
    </div>
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
        <tr v-for="persona in personas" :key="persona.id">
          <td v-if="editando === persona.id">
            <input type="text" class="form-control" v-model="persona.nombre" id="persona.nombre"
              data-cy="persona-nombre" />
          </td>
          <td v-else>
            {{ persona.nombre }}
          </td>
          <td v-if="editando === persona.id">
            <input type="text" class="form-control" v-model="persona.apellido" />
          </td>
          <td v-else>
            {{ persona.apellido }}
          </td>
          <td v-if="editando === persona.id">
            <input type="email" class="form-control" v-model="persona.email" />
          </td>
          <td v-else>
            {{ persona.email }}
          </td>
          <td v-if="editando === persona.id">
            <button class="btn btn-success" data-cy="save-button" @click="guardarPersona(persona)"> &#x1F5AB; Guardar </button>
            <button class="btn btn-secondary ml-2" data-cy="cancel-button" @click="cancelarEdicion(persona)"> &#x1F5D9; Cancelar </button>
          </td>
          <td v-else>
            <button class="btn btn-info" data-cy="edit-button" @click="editarPersona(persona)"> &#x1F58A; Editar </button>
            <button class="btn btn-danger ml-2" data-cy="delete-button" @click="$emit('delete-persona', persona.id)"> &#x1F5D1; Eliminar </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
// Definicion del componente Vue
import { ref } from "vue";
export default {
  // Nombre del componente, en este caso 'tabla-personas'
  name: "tabla-personas",
  // Propiedades que puede recibir el componente
  props: {
    // La propiedad 'personas' se espera que sea un array
    personas: Array,
  },
  setup(props, ctx) {
    const editando = ref(null);
    const personaEditada = ref(null);
    const editarPersona = (persona) => {
      personaEditada.value = { ...persona };
      editando.value = persona.id;
    };
    const guardarPersona = (persona) => {
      if (
        !persona.nombre.length ||
        !persona.apellido.length ||
        !persona.email.length
      ) {
        return;
      }
      ctx.emit("actualizar-persona", persona.id, persona);
      editando.value = null;
    };
    const cancelarEdicion = (persona) => {
      Object.assign(persona, personaEditada.value);
      editando.value = null;
    };
    return {
      editando,
      editarPersona,
      guardarPersona,
      cancelarEdicion,
    };
  },
};
</script>
<style scoped></style>
