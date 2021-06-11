<template>
  <v-app id="inspire">
    <v-navigation-drawer v-model="drawer" app clipped>
      <v-list dense>
        <v-list-item router :to="{name: 'blog'}" exact>
          <v-list-item-action>
            <v-icon>fas fa-arrow-alt-circle-right</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Tech Blogs</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item router :to="{name: 'dictionary'}" exact>
          <v-list-item-action>
            <v-icon>fas fa-arrow-alt-circle-right</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Document Dictionary</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <!--        <v-list-item router :to="{name: 'bookmark'}" exact>-->
        <v-list-item @click="notReady">
          <v-list-item-action>
            <v-icon>fas fa-arrow-alt-circle-right</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Bookmarks</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
<!--        <v-list-item router :to="{name: 'manager'}" exact>-->
        <v-list-item @click="notReady">
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
      <v-spacer></v-spacer>
      <v-tab>
        <v-badge color="pink" dot>
          DAU <br />
          {{ numberFormat(visitors.day) }}
        </v-badge>
      </v-tab>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <v-tab>
        <v-badge color="teal" dot>
          TOTAL <br />
          {{ numberFormat(visitors.reduce) }}
        </v-badge>
      </v-tab>
    </v-app-bar>

    <v-main>
      <v-container fill-height fluid align-center>
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-footer dark padless>
      <v-card class="flex" flat tile>
        <v-card-title class="teal justify-center">
          <v-btn v-for="icon in icons" :key="icon" class="mx-4" dark icon href="https://github.com/shirohoo/development-dictionary" target="_blank">
            <v-icon size="24px">
              {{ icon }}
            </v-icon>
            <strong class="font-italic text-caption">&nbsp;V3.0 # </strong>
          </v-btn>
          <strong class="subheading font-italic text-caption"> Only I can change my life. No one can do it for me.</strong>
        </v-card-title>
      </v-card>
    </v-footer>
  </v-app>
</template>

<script>
import Vuetify    from "vuetify";
import {mapState} from "vuex";

export default {
  name   : 'App',
  vuetify: new Vuetify(),
  props  : {},

  data: () => ({
    drawer: null,
    icons : [
      'mdi-github',
    ],
  }),
  created(){
    this.$vuetify.theme.dark = true;
  },
  computed: {
    ...mapState(["visitors"]),
    numberFormat(){
      return (num) => {
        let regexp = /\B(?=(\d{3})+(?!\d))/g;
        return num.toString().replace(regexp, ',');
      };
    },
  },
  methods : {
    notReady(){
      alert('준비중입니다.')
    }
  }
};
</script>
