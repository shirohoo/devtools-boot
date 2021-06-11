<template>
  <v-container fill-height fluid>
    <!-- search area -->
    <v-row justify="center">
      <v-col cols="2">
      </v-col>
      <v-col>
        <v-text-field label="English" v-model="search.enWord" @keyup="findContents"></v-text-field>
        <v-text-field label="Korean" v-model="search.krWord" @keyup="findContents"></v-text-field>
      </v-col>
      <v-col class="pa-3" cols="3" align-self="center">
        <v-btn class="ma-3" color="purple" dark large outlined @click="resetSearchForm">
          <v-icon dark>fas fa-trash-restore-alt</v-icon>
        </v-btn>
      </v-col>
      <v-col cols="1">
      </v-col>
    </v-row>

    <!-- total post & size area -->
    <v-row justify="center">
      <v-col cols="1">
      </v-col>
      <v-col align-self="center" class="text-h6">
        <v-tooltip top>
          <template v-slot:activator="{ on, attrs }">
            <v-icon dark v-bind="attrs" v-on="on" large color="pink">
              fas fa-carrot
            </v-icon>
            &nbsp;
            {{ numberFormat(pager.totalElements) }}
          </template>
          <span>words</span>
        </v-tooltip>
      </v-col>
      <v-col cols="3">
        <v-select v-model="search.size" :items="display" label="Display" @change="findContents(1);"></v-select>
      </v-col>
      <v-col cols="1">
      </v-col>
    </v-row>

    <!-- board area -->
    <v-row class="text-center pa-3" justify="center">
      <v-col>
        <v-simple-table class="rounded-lg" fixed-header height="100%">
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-center rounded-tl-lg" style="width: 20%;">English</th>
                <th class="text-center" style="width: 40%;">Korean</th>
                <th class="text-center" style="width: 20%;">Picture</th>
                <th class="text-center rounded-tr-lg" style="width: 20%;">Google</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="content in contents">
                <td class="yellow--text">
                  {{ content.enWord }}
                </td>
                <td class="shades--text">
                  {{ content.krWord }}
                </td>
                <td class="text-center">
                  <a class="text-decoration-none" :href="'https://www.google.com/search?q=' + content.enWord + '&newwindow=1&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjwgc7Lj83wAhXHFogKHYUABiYQ_AUoAXoECAEQAw&biw=1920&bih=937'" target="blank">
                    <v-icon>
                      fas fa-images
                    </v-icon>
                  </a>
                </td>
                <td>
                  <a class="text-decoration-none" :href="'https://www.google.com/search?q=' + content.enWord + '&rlz=1C1GCEA_koKR939KR939&oq=taglib&aqs=chrome..69i57j0l6j69i60.1267j0j4&sourceid=chrome&ie=UTF-8'" target="blank">
                    <v-icon>
                      mdi-google
                    </v-icon>
                  </a>
                </td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
    </v-row>

    <!-- pagination area -->
    <v-row justify="center">
      <v-col>
        <v-pagination color="teal"
                      :value="pager.currentPage"
                      :length="pager.total"
                      :total-visible="9"
                      @input="findContents"
        ></v-pagination>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import axios        from 'axios';
import {mapActions} from "vuex";

export default {
  name: 'Dictionary',
  data(){
    return {
      search  : {
        page  : 0,
        size  : 10,
        enWord: '',
        krWord: ''
      },
      contents: {},
      pager   : {
        currentPage  : 0,
        total        : 0,
        totalElements: 0,
      },
      display : [10, 30, 50, 100, 200],
    }
  },
  mounted(){
    this.findContents();
  },
  computed: {
    numberFormat(){
      return (num) => {
        let regexp = /\B(?=(\d{3})+(?!\d))/g;
        return num.toString().replace(regexp, ',');
      };
    },
  },
  methods : {
    ...mapActions(["getCurrentVisitors"]),
    findContents(page){
      if(page !== undefined){
        this.search.page = page - 1;
      }
      let url = '/api/v1/dictionaries';
      axios.get(url, {
        params: {
          page  : this.search.page,
          size  : this.search.size,
          enWord: this.search.enWord,
          krWord: this.search.krWord
        }
      }).then(res => {
        this.contents = res.data.pages.content;

        this.getCurrentVisitors(res);

        this.pager.currentPage = res.data.pages.number + 1;
        this.pager.total = res.data.pages.totalPages;
        this.pager.totalElements = res.data.pages.totalElements;

        // 페이징 버그 방지
        if(this.search.page === 0 && this.pager.total === 0) return;
        if(this.search.page >= this.pager.total){
          this.search.page = this.pager.total - 1;
          this.findContents();
        }
      }).catch(() => {
        alert('400, Bad Request')
      });
    },
    resetSearchForm(){
      this.search.page = 0;
      this.search.size = 10;
      this.search.company = '';
      this.search.title = '';
      this.findContents(1);
    },
    isBetweenDay(regDate){
      let regDateArray = String(regDate).split('-');
      let date = new Date(regDateArray[0] *= 1, regDateArray[1] - 1, regDateArray[2] *= 1)

      let nowDate = new Date();
      let betweenDay = Math.floor((nowDate.getTime() - date.getTime()) / 1000 / 60 / 60 / 24);
      return betweenDay === 0;
    },
  },
};
</script>
