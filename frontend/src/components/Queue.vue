<template>
  <v-container>
    <h1>Queue Management</h1>
    <v-row>
      <v-col sm="12">
        <h3>Queue</h3>
        <v-data-table
            :headers="headers"
            :items="triage"
            :server-items-length="totalTriages"
            class="elevation-1"
        >
          <template v-slot:top>
              <v-dialog v-model="dialogTreatPatient" max-width="600px">
                  <v-card>
                      <v-card-title class="text-h5">Are you sure you want to treat this patient?</v-card-title>
                      <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn color="blue darken-1" text @click="closeTreatPatientDialog">Cancel</v-btn>
                          <v-btn color="blue darken-1" text @click="treatPatientConfirm">OK</v-btn>
                          <v-spacer></v-spacer>
                      </v-card-actions>
                  </v-card>
              </v-dialog>
          </template>

          <template v-slot:item.age="{ item }">
              {{ getAgeInYears(item.patient.date_of_birth) }}
          </template>

          <template v-slot:item.actions="{ item }">
              <v-btn
                  v-if="item.is_treated==false"
                  color="blue"
                  @click="treatPatientCheck(item)"
              >
                  Treat
              </v-btn>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
    <v-snackbar
        v-model="snackbar"
        :color="color"
        :top="true"
    >
        {{ displayMessage }}
        <v-btn
            dark
            text
            @click="snackbar = false"
        >
            Close
        </v-btn>
    </v-snackbar>
  </v-container>
</template>

<script>
import api from "@/service/apiService";
const Querystring = require('querystring');

export default 
{
    name: "Sale",
    data: vm => ({
        headers: [
            { text: 'ID', value: 'id', sortable: false },
            { text: 'First Name', value: 'patient.first_name' },
            { text: 'Last Name', value: 'patient.last_name' },
            { text: 'Priority', value: 'priority.name' },
            { text: 'BP', value: 'blood_pressure' },
            { text: 'Temp', value: 'temperature' },
            { text: 'Weight', value: 'weight' },
            { text: 'Age', value: 'age' },
            { text: 'Actions', value: 'actions', sortable: false },
        ],
        triage: [],
        responseSuccess: false,
        snackbar: false,
        displayMessage: "",
        color: 'general',
        triageID: 0,
        dialogTreatPatient: false,
        totalTriages: 2,
    }),
    methods: {
        getData() {
            this.$http.get('/api/v1/triage/queue', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            })
            .then(response => {
                this.triage = response.data;
                this.totalTriages = Object.keys(response.data).length;
                console.log('Total Queue Size :: ' + JSON.stringify(this.totalTriages));
            }
            )
            .catch(error => console.log('Triage Get Error:: ' + error))
        },
        getAgeInYears(date_of_birth)
        {
            let tmp = 0;
            let current_date = new Date();
            let dob = new Date(date_of_birth);
            var diff =(current_date.getTime() - dob.getTime()) / 1000;
            diff /= (60 * 60 * 24);
            tmp = Math.abs(Math.round(diff/365.25));
            return(tmp);
        },
        treatPatientCheck(item)
        {
            this.triageID = item.id;
            this.dialogTreatPatient = true;
            console.log('Ready to treat patient :: ' + JSON.stringify(item));
        },
        closeTreatPatientDialog()
        {
            this.dialogTreatPatient = false;
        },
        treatPatientConfirm()
        {
            this.closeTreatPatientDialog();
            this.treatPatient();
        },
        treatPatient()
        {
            //NB: we are sending form data not JSON
            const requestData = {
                triageId: this.triageID,
            };
            let body = Querystring['stringify'](requestData);
            const config = {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                }
            }
            this.$http.post('/api/v1/triage/treat', body, config)
                .then(response => {
                    this.getData();
                    this.displayMessage = "Patient Treated Successfully";
                    this.snackbar = true;
                    console.log('Patient Treated Successfully');
                }
            )
            .catch(error => {
                console.log('Treat Patient Error:: ' + error)
                this.displayMessage = "Error: Failed to treat patient";
                this.snackbar = true;
            })
        },
    },
    mounted() {
        this.getData();
    },
    created() {
        this.getData();
    },
};
</script>