<template>
  <div id="formulario-persona">
    <form @submit.prevent="enviarFormulario">
      <div class="container">
        <div class="row">
          <div class="col-md-4">
            <div class="form-group">
              <label>Nombre</label>
              <input
                ref="nombre"
                v-model="persona.nombre"
                type="text"
                class="form-control"
                data-cy="name"
                :class="{ 'is-invalid': procesando && nombreInvalido }"
                @focus="resetEstado"
                @keypress="resetEstado"
              >
            </div>
          </div>
          <div class="col-md-4">
            <div class="form-group">
              <label>Apellido</label>
              <input
                v-model="persona.apellido"
                type="text"
                class="form-control"
                data-cy="surname"
                :class="{ 'is-invalid': procesando && apellidoInvalido }"
                @focus="resetEstado"
              >
            </div>
          </div>
          <div class="col-md-4">
            <div class="form-group">
              <label>Email</label>
              <input
                v-model="persona.email"
                type="email"
                class="form-control"
                data-cy="email"
                :class="{ 'is-invalid': procesando && emailInvalido }"
                @focus="resetEstado"
              >
            </div>
          </div>
        </div>
        <br>
        <div class="row">
          <div class="col-md-4">
            <div class="form-group">
              <button
                class="btn btn-primary"
                data-cy="add-button"
              >
                Añadir persona
              </button>
            </div>
          </div>
        </div>
      </div>
      <br>
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <div
              v-if="error && procesando"
              class="alert alert-danger"
              role="alert"
            >
              Debes rellenar todos los campos!
            </div>
            <div
              v-if="correcto"
              class="alert alert-success"
              role="alert"
            >
              La persona ha sido agregada correctamente!
            </div>
          </div>
        </div>
      </div>
    </form>
  </div>
</template>
<script>
import { ref, defineEmits, computed } from "vue";
export default {
    name: "FormularioPersona",
    emits: ['add-persona'],
    setup(props, ctx) {
        const nombreInvalido = computed(() => persona.value.nombre.length < 1);
        const apellidoInvalido = computed(() => persona.value.apellido.length < 1);
        const emailInvalido = computed(() => persona.value.email.length < 1);
        const procesando = ref(false);
        const correcto = ref(false);
        const error = ref(false);
        const persona = ref({
            nombre: "",
            apellido: "",
            email: "",
        });
        const nombre = ref(null);
        const emit = defineEmits();
        const enviarFormulario = () => {
            procesando.value = true;
            resetEstado();
            if (
                nombreInvalido.value ||
                apellidoInvalido.value ||
                emailInvalido.value
            ) {
                error.value = true;
                return;
            }
            ctx.emit('add-persona', persona.value);
            nombre.value.focus();
            persona.value = {
                nombre: "",
                apellido: "",
                email: "",
            };
            error.value = false;
            correcto.value = true;
            procesando.value = false;
        };
        const resetEstado = () => {
            correcto.value = false;
            error.value = false;
        };
        return {
            procesando,
            correcto,
            error,
            persona,
            nombre,
            enviarFormulario,
            resetEstado,
            emit,
            nombreInvalido,
            apellidoInvalido,
            emailInvalido,
        };
    },
};
</script>
<style scoped>
/* Estilos especificos del componente con el modificador "scoped" */
form {
    margin-bottom: 2rem;
}

button {
    background: #009435;
    border: 1px solid #009435;
}
</style>