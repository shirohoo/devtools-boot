<template>
  <v-app id="inspire">
    <v-navigation-drawer v-model="drawer" app clipped>
      <v-list dense>
        <v-list-item router-link :to="{name: 'blog'}" exact>
          <v-list-item-action>
            <v-icon>fas fa-arrow-alt-circle-right</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Tech Blogs</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item router-link :to="{name: 'bookmark'}" exact>
          <v-list-item-action>
            <v-icon>fas fa-arrow-alt-circle-right</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Bookmarks</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item router-link :to="{name: 'dictionary'}" exact>
          <v-list-item-action>
            <v-icon>fas fa-arrow-alt-circle-right</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Open Source Dictionary</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item router-link :to="{name: 'manager'}" exact>
          <v-list-item-action>
            <v-icon>fas fa-lock</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Manager</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app clipped-left>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>DEVELOPMENT DICTIONARY</v-toolbar-title>
    </v-app-bar>

    <v-main>
      <v-container class="fill-height" fluid>
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-footer dark padless>
      <v-card class="flex" flat tile>
        <v-card-title class="teal">
          <strong class="subheading">🐱‍🏍 No one can do it for me, Only I can change my life.</strong>

          <v-spacer></v-spacer>

          <v-btn v-for="icon in icons" :key="icon" class="mx-4" dark icon href="https://github.com/shirohoo/development-dictionary" target="_blank">
            <v-icon size="24px">
              {{ icon }}
            </v-icon>
          </v-btn>
        </v-card-title>
      </v-card>
    </v-footer>
  </v-app>
</template>

<script>
import Vuetify from "vuetify";

export default {
  name   : 'App',
  vuetify: new Vuetify(),
  props  : {
    source: String,
  },

  data: () => ({
    drawer: null,
    icons : [
      'mdi-github',
    ],
  }),

  created(){
    this.$vuetify.theme.dark = true
  },
};
</script>
