<template>
  <v-container>
    <h1>Patient Management</h1>
    <p>Patients</p>
    <v-row>
      <v-col sm="12">
        <v-alert v-if="responseSuccess" dense text type="success">
          You have successfully added a patient
        </v-alert>
      </v-col>
      <v-col sm="4">
        <h3>Patient Creation</h3>
        <v-text-field
          v-model="patientCreation.firstName"
          label="First Name"
          :rules="nameRules"
        ></v-text-field>
        <v-text-field
          v-model="patientCreation.lastName"
          label="Last Name"
          :rules="nameRules"
        ></v-text-field>
        <v-text-field
          v-model="patientCreation.address"
          label="Address"
          :rules="nameRules"
        ></v-text-field>
        <v-text-field
          v-model="patientCreation.email"
          label="Email"
          :rules="nameRules"
        ></v-text-field>
        <v-text-field
          v-model="patientCreation.phone"
          label="Phone"
          :rules="nameRules"
        ></v-text-field>
        <!-- <v-date-picker
          v-model="patientCreation.dob"
          label="Date Of Birth"
          color="green lighten-1"
        ></v-date-picker> -->
        <v-menu
            v-model="datemenu"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="auto"
        >
            <template v-slot:activator="{ on, attrs }">
                <v-text-field
                    v-model="patientCreation.dob"
                    label="Date Of Birth"
                    prepend-icon="mdi-calendar"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                ></v-text-field>
            </template>
            <v-date-picker
                v-model="patientCreation.dob"
                color="green lighten-1"
                @input="datemenu = false"
            ></v-date-picker>
        </v-menu>
        <v-select
          v-model="patientCreation.country_id"
          :items="countries"
          label="Choose Country"
          filled
          outlined
          dense
          no-data-text="There are no countries"
          value=0
          @change="onChangeCountry"
        ></v-select>
        <v-select
          v-model="patientCreation.gender_id"
          :items="genders"
          label="Choose Gender"
          filled
          outlined
          dense
          no-data-text="There are no genders"
          value=0
          @change="onChangeGender"
        ></v-select>
        <v-btn color="blue" v-on:click="savePatient">
          Create Patient
        </v-btn>
      </v-col>
      <v-col sm="8">
        <h3>Patients</h3>
        <v-data-table
            :headers="headers"
            :items="patients"
            sort-by="id"
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
    name: "Patient",
    data: vm => ({
        patientCreation: 
        {
            country_id: 0,
            gender_id: 0,
            firstName: "",
            lastName: "",
            address: "",
            email: "",
            phone: "",
            dob: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
        },
        patients: [],
        countries: [],
        genders: [],
        responseSuccess: false,
        snackbar: false,
        displayMessage: "",
        color: 'general',
        headers: [
            { text: 'ID', value: 'id' },
            { text: 'First Name', value: 'first_name' },
            { text: 'Last Name', value: 'last_name' },
            { text: 'Email', value: 'email' },
            { text: 'Phone', value: 'phone' },
            { text: 'DOB', value: 'date_of_birth' },
            { text: 'Gender', value: 'gender.name' },
        ],
        quantityRules: [
            v => String(v).length > 0 || 'This field may not be empty',
            v => Number.isInteger(Number(v)) || 'The value must be an integer number',
            v => v > 0 || 'The value must be greater than zero'
        ],
        nameRules: [
            v => String(v).length > 0 || 'This field may not be empty',
        ],
        date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
        datemenu: false,
    }),
    methods: {
        getData() {
            this.$http.get('/api/v1/patient', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            })
            .then(response => {
                this.patients = response.data;
            }
            )
            .catch(error => console.log('Patient Get Error:: ' + error))
        },
        getCountries() {
            this.$http.get('/api/v1/country', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            })
            .then(response => {
                this.countries = this.getCountryIds(this.decodeCountryNames(response.data), this.decodeCountryIds(response.data));
            }
            )
            .catch(error => console.log('Countries Get Error:: ' + error))
        },
        getGenders() {
            this.$http.get('/api/v1/gender', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            })
            .then(response => {
                this.genders = this.getGenderIds(this.decodeGenderNames(response.data), this.decodeGenderIds(response.data));
            }
            )
            .catch(error => console.log('Genders Get Error:: ' + error))
        },
        getCountryIds(country_names, country_ids)
        {
            let tmp = [];
            for(let i = 0; i < country_ids.length; i++)
            {
                tmp.push({'value': country_ids[i], 'text': country_names[i]});
            }
            return(tmp);
        },
        decodeCountryNames(data) {
            let types = data.map(p => p.name);
            return(types);
        },
        decodeCountryIds(data) {
            let ids = data.map(p => p.id);
            return(ids);
        },
        getGenderIds(gender_names, gender_ids)
        {
            let tmp = [];
            for(let i = 0; i < gender_ids.length; i++)
            {
                tmp.push({'value': gender_ids[i], 'text': gender_names[i]});
            }
            return(tmp);
        },
        decodeGenderNames(data) {
            let types = data.map(p => p.name);
            return(types);
        },
        decodeGenderIds(data) {
            let ids = data.map(p => p.id);
            return(ids);
        },
        onChangeCountry()
        {
            console.log('Got Selected Country As:: ' + JSON.stringify(this.patientCreation.country_id));
        },
        onChangeGender()
        {
            console.log('Got Selected Gender As:: ' + JSON.stringify(this.patientCreation.gender_id));
        },
        savePatient() {
            const requestData = {
                country_id: this.patientCreation.country_id,
                gender_id: this.patientCreation.gender_id,
                first_name: this.patientCreation.firstName,
                last_name: this.patientCreation.lastName,
                address: this.patientCreation.address,
                email: this.patientCreation.email,
                phone: this.patientCreation.phone,
                date_of_birth: this.patientCreation.dob,
            };
            this.$http.post('/api/v1/patient', requestData, {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('token')
                    },
                })
                .then(response => {
                    this.getData();
                    this.displayMessage = "Patient Saved Successfully";
                    this.snackbar = true;
                    console.log('Patient saved successfully');
                }
            )
            .catch(error => {
                console.log('Patient Save Error:: ' + error)
                this.displayMessage = "Error: Failed to Save Patient";
                this.snackbar = true;
            })
        },
    },
    mounted() {
        this.getData();
        this.getCountries();
        this.getGenders();
    },
    created() {
        this.getData();
        this.getCountries();
        this.getGenders();
    },
    watch: {
        patientCreation: {
            handler () {
                console.log('Got Selected Country As:: ' + JSON.stringify(this.patientCreation.country_id));
                console.log('Got Selected Gender As:: ' + JSON.stringify(this.patientCreation.gender_id));
            }
        },
    },
};
</script>