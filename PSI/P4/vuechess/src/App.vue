<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="wrapper">
      <ul>
        <li>
          <RouterLink to="/">sign-up</RouterLink>
        </li>
        <li>
          <RouterLink to="/">log-in</RouterLink>
        </li>
        <li>
          <RouterLink to="/">my-view</RouterLink>
        </li>
        <li>
          <RouterLink to="/">my-view</RouterLink>
        </li>
      </ul>
    </div>
  </header>
  <RouterView />
</template>