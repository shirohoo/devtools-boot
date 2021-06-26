<template >
  <v-container fill-height fluid >
    <!-- search area -->
    <v-row justify="center" >
      <v-col cols="2" >
      </v-col >
      <v-col >
        <v-select
            v-model="search.category"
            :items="categories"
            label="Category"
            @change="findContents"
        />
        <v-text-field
            label="Title"
            v-model="search.title"
            @keyup.enter="findContents"
        />
      </v-col >
      <v-col class="pa-3" cols="3" >
        <v-btn
            class="ma-3"
            color="purple"
            dark
            large
            outlined
            @click="resetSearchForm"
        >
          <v-icon dark >fas fa-trash-restore-alt</v-icon >
        </v-btn >
        <br />
        <v-btn
            class="ma-3"
            color="purple"
            dark
            large
            outlined
            @click="findContents"
        >
          <v-icon dark >fas fa-search</v-icon >
        </v-btn >
      </v-col >
      <v-col cols="1" >
      </v-col >
    </v-row >

    <!-- total post & size area -->
    <v-row justify="center" >
      <v-col cols="1" >
      </v-col >
      <v-col align-self="center" class="text-h6" >
        <v-tooltip top >
          <template v-slot:activator="{ on, attrs }" >
            <v-icon dark v-bind="attrs" v-on="on" large color="teal" >
              fas fa-newspaper
            </v-icon >
            &nbsp;
            {{ numberFormat(pager.totalElements) }}
          </template >
          <span >articles</span >
        </v-tooltip >
      </v-col >
      <v-col cols="3" >
        <v-select
            v-model="search.size"
            :items="display"
            label="Display"
            @change="findContents(1);"
        />
      </v-col >
      <v-col cols="1" >
        <v-btn
            @click="createState = !createState"
            fab
            dark
            color="teal"
        >
          <v-icon dark >
            mdi-plus
          </v-icon >
        </v-btn >
      </v-col >
      <v-col cols="1" ></v-col >
    </v-row >

    <!-- board area -->
    <v-row class="text-center pa-3" justify="center" >
      <v-col >
        <v-simple-table class="rounded-lg" fixed-header height="100%" >
          <template v-slot:default >
            <thead >
            <tr >
              <th class="text-center rounded-tl-lg" style="width: 20%;" >Category</th >
              <th class="text-center" style="width: 70%;" >Title</th >
              <th class="text-center" style="width: 10%;" >Delete</th >
            </tr >
            </thead >
            <tbody >
            <tr v-for="content in contents" >
              <td >
                <div class="text-h6" style="color: paleturquoise; font-weight: bold" >
                  {{ content.category }}
                </div >
              </td >
              <td class="text-left" >
                <a
                    :href="content.link"
                    target="blank"
                    class="text-decoration-none"
                    style="color: white;
                      font-weight: bold"
                >
                  {{ content.title }}
                </a >
              </td >
              <td >
                <v-icon
                    @click="deleteBookmark(content.id)"
                >
                  fas fa-trash-alt
                </v-icon >
              </td >
            </tr >
            </tbody >
          </template >
        </v-simple-table >
      </v-col >
    </v-row >

    <!-- pagination area -->
    <v-row justify="center" >
      <v-col >
        <v-pagination
            color="teal"
            :value="pager.currentPage"
            :length="pager.total"
            :total-visible="9"
            @input="findContents"
        />
      </v-col >
    </v-row >

    <!-- create area -->
    <v-sheet
        v-if="createState" style="position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); z-index: 999"
        class="px-7 pt-7 pb-4 mx-auto text-center d-inline-block"
        elevation="6"
        rounded
        color="blue-grey darken-3"
        dark
    >
      <div class="grey--text text--lighten-1 text-body-2 mb-4" >
        <v-select
            label="Category"
            v-model="create.category"
            :items="categories"
        />
        <v-text-field
            label="Title"
            v-model="create.title"
        />
        <v-text-field
            label="Link"
            v-model="create.link"
        />
      </div >
      <v-btn
          :loading="loading"
          class="ma-1"
          color="primary"
          plain
          @click="createRequest"
      >
        Create
      </v-btn >
      <v-btn
          :disabled="loading"
          class="ma-1"
          color="grey"
          plain
          @click="cancelCreate"
      >
        Cancel
      </v-btn >
    </v-sheet >

    <!-- remove area -->
    <v-sheet v-if="deleteState" style="position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); z-index: 999"
             class="px-7 pt-7 pb-4 mx-auto text-center d-inline-block"
             elevation="6"
             rounded
             color="blue-grey darken-3"
             dark
    >
      <div class="grey--text text--lighten-1 text-body-2 mb-4" >
        Are you sure you want to delete this?
      </div >
      <v-btn
          :loading="loading"
          class="ma-1"
          color="error"
          plain
          @click="remove"
      >
        Delete
      </v-btn >
      <v-btn
          @click="deleteState = !deleteState"
          :disabled="loading"
          class="ma-1"
          color="grey"
          plain
      >
        Cancel
      </v-btn >
    </v-sheet >

  </v-container >
</template >

<script >
import axios from "axios";
import {mapActions} from "vuex";

export default {
  name: "Bookmark",
  data() {
    return {
      id         : 0,
      deleteState: false,
      createState: false,
      loading    : false,
      create     : {
        category: null,
        title   : null,
        link    : null
      },
      search     : {
        page    : 0,
        size    : 10,
        category: '',
        title   : ''
      },
      contents   : {},
      pager      : {
        currentPage  : 0,
        total        : 0,
        totalElements: 0,
      },
      categories : ["Java", "Spring", "Build Tool", "Database", "CS", "Git", "IDE", "Infra", "Javascript", "Vue"],
      display    : [10, 30, 50, 100, 200],
    }
  },
  mounted() {
    this.findContents();
  },
  computed: {
    numberFormat() {
      return (num) => {
        let regexp = /\B(?=(\d{3})+(?!\d))/g;
        return num.toString().replace(regexp, ',');
      };
    },
  },
  methods : {
    ...mapActions(["getCurrentVisitors"]),

    findContents(page) {
      if (page !== undefined) {
        this.search.page = page - 1;
      }

      let url = "/api/v1/bookmarks";

      axios.get(url, {
        params: this.search
      }).then(res => {
        this.contents = res.data.pages.content;
        this.getCurrentVisitors(res);
        this.pager.currentPage = res.data.pages.number + 1;
        this.pager.total = res.data.pages.totalPages;
        this.pager.totalElements = res.data.pages.totalElements;

        // 페이징 버그 방지
        if (this.search.page === 0 && this.pager.total === 0) return;
        if (this.search.page >= this.pager.total) {
          this.search.page = this.pager.total - 1;
          this.findContents();
        }
      }).catch(() => {
        alert("400, Bad Request!");
      });
    },

    resetSearchForm() {
      this.search.page = 0;
      this.search.size = 10;
      this.search.category = '';
      this.search.title = '';
      this.findContents(1);
    },

    createRequest() {
      this.loading = true;
      let url = "/api/v1/bookmark";

      axios.post(url, null, {
        params: this.create
      }).then(res => {
        if (res.request.responseURL.includes("login")) {
          window.location.href = "/login";
          return;
        }
        alert("Successes create!");
        this.findContents();
        this.loading = false;
        this.createState = false;
      }).catch(err => {
        alert("The value must not be empty!");
        this.loading = false;
        this.createState = false;
      });
    },

    cancelCreate() {
      this.create.category = null;
      this.create.title = null;
      this.create.link = null;
      this.createState = !this.createState;
    },

    deleteBookmark(id) {
      this.id = id;
      this.deleteState = true;
    },

    remove() {
      this.loading = true;
      let url = "/api/v1/bookmark/" + this.id;

      axios.delete(url, {})
           .then(res => {
             alert("Successes delete!");
             this.loading = false;
             this.deleteState = false;
             this.findContents();
           })
           .catch(err => {
             window.location.href = "/login";
           });
    },
  },
};
</script >
