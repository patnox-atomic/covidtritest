<template>
  <v-container>
    <h1>Triage Management</h1>
    <p>Triage</p>
    <v-row>
      <v-col sm="12">
        <v-alert v-if="responseSuccess" dense text type="success">
          Triage: You have successfully added a record
        </v-alert>
      </v-col>
      <v-col sm="6">
        <h3>Triage Creation</h3>
        <v-select
          v-model="triageCreation.patient_id"
          :items="patients"
          label="Choose Patient"
          filled
          outlined
          dense
          no-data-text="There are no patients"
          value=0
          @change="onChangePatient"
        ></v-select>
        <v-text-field
          v-model="triageCreation.blood_pressure"
          label="Blood Pressure"
          :rules="nameRules"
        ></v-text-field>
        <v-text-field
          v-model="triageCreation.temperature"
          label="Temperature"
          :rules="nameRules"
        ></v-text-field>
        <v-text-field
          v-model="triageCreation.weight"
          label="Weight"
          :rules="nameRules"
        ></v-text-field>
        <v-text-field
          v-model="triageCreation.misc_symptoms"
          label="Misc Symptoms"
          :rules="nameRules"
        ></v-text-field>
        <v-select
          v-model="triageCreation.priority_id"
          :items="priorities"
          label="Choose Priority"
          filled
          outlined
          dense
          no-data-text="There are no priorities"
          value=0
          @change="onChangePriority"
        ></v-select>
        <v-btn color="blue" v-on:click="createTriage">
          Create Triage
        </v-btn>
      </v-col>
      <v-col sm="6">
        <h3>Triage Records</h3>
        <v-data-table
            :headers="headers"
            :items="triage"
            :sort-by="['id',]"
            :sort-desc="[true,]"
            class="elevation-1"
        >
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
    name: "Triage",
    data: vm => ({
        triageCreation: 
        {
            patient_id: "",
            priority_id: "",
            blood_pressure: "",
            temperature: "",
            weight: "",
            misc_symptoms: "",
        },
        triage: [],
        patients: [],
        priorities: [],
        responseSuccess: false,
        snackbar: false,
        displayMessage: "",
        color: 'general',
        headers: [
            { text: 'ID', value: 'id' },
            { text: 'First Name', value: 'patient.first_name' },
            { text: 'Last Name', value: 'patient.last_name' },
            { text: 'Priority', value: 'priority.name' },
            { text: 'BP', value: 'blood_pressure' },
            { text: 'Temp', value: 'temperature' },
            { text: 'Weight', value: 'weight' },
        ],
        nameRules: [
            v => String(v).length > 0 || 'This field may not be empty',
        ],
    }),
    methods: {
        getData() {
            this.$http.get('/api/v1/triage', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            })
            .then(response => {
                this.triage = response.data;
            }
            )
            .catch(error => console.log('Triage Get Error:: ' + error))
        },
        getPatients() {
            this.$http.get('/api/v1/patient', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            })
            .then(response => {
                this.patients = this.getPatientIds(this.decodePatientNames(response.data), this.decodePatientIds(response.data));
            }
            )
            .catch(error => console.log('Patient Get Error:: ' + error))
        },
        getPriorities() {
            this.$http.get('/api/v1/priority', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            })
            .then(response => {
                this.priorities = this.getPriorityIds(this.decodePriorityNames(response.data), this.decodePriorityIds(response.data));
            }
            )
            .catch(error => console.log('Priorities Get Error:: ' + error))
        },
        getPatientIds(patient_names, patient_ids)
        {
            let tmp = [];
            for(let i = 0; i < patient_ids.length; i++)
            {
                tmp.push({'value': patient_ids[i], 'text': patient_names[i]});
            }
            return(tmp);
        },
        decodePatientNames(data) {
            let types = data.map(p => p.first_name + ' ' + p.last_name);
            return(types);
        },
        decodePatientIds(data) {
            let ids = data.map(p => p.id);
            return(ids);
        },
        getPriorityIds(priority_names, priority_ids)
        {
            let tmp = [];
            for(let i = 0; i < priority_ids.length; i++)
            {
                tmp.push({'value': priority_ids[i], 'text': priority_names[i]});
            }
            return(tmp);
        },
        decodePriorityNames(data) {
            let types = data.map(p => p.name);
            return(types);
        },
        decodePriorityIds(data) {
            let ids = data.map(p => p.id);
            return(ids);
        },
        onChangePatient()
        {
            console.log('Got Selected Patient As:: ' + JSON.stringify(this.triageCreation.patient_id));
        },
        onChangePriority()
        {
            console.log('Got Selected Priority As:: ' + JSON.stringify(this.triageCreation.priority_id));
        },
        createTriage() {
            const requestData = {
                patient_id: this.triageCreation.patient_id,
                priority_id: this.triageCreation.priority_id,
                blood_pressure: this.triageCreation.blood_pressure,
                temperature: this.triageCreation.temperature,
                weight: this.triageCreation.weight,
            };
            this.$http.post('/api/v1/triage', requestData, {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('token')
                    },
                })
                .then(response => {
                    this.getData();
                    this.displayMessage = "Record Saved Successfully";
                    this.snackbar = true;
                    console.log('Record saved successfully');
                }
            )
            .catch(error => {
                console.log('Record Save Error:: ' + error)
                this.displayMessage = "Error: Failed to Save Record";
                this.snackbar = true;
            })
        },
    },
    mounted() {
        this.getData();
        this.getPatients();
        this.getPriorities();
    },
    created() {
        this.getData();
        this.getPatients();
        this.getPriorities();
    },
};
</script>