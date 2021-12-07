//patnox

import { jsPDF } from "jspdf";
import 'jspdf-autotable';
import XLSX from 'xlsx';
//import flatten from 'flat';
import { flatten, unflatten } from 'flat';
import { AlignmentType, PageNumberFormat, Document, HeadingLevel, Packer, Paragraph, TabStopPosition, TabStopType, TextRun, Table, TableCell, TableRow, FileSaver, WidthType, PageOrientation, Header, Footer, PageNumber, ShadingType } from "docx";
//import { get } from 'lodash.get';
import lodashGet from 'lodash.get';
//import * as docx from "docx";
import * as fs from "fs";
import { saveAs } from 'file-saver';

export const utils = {
    jsonFieldsXLS: (data) => {
        let obj = {};
        // let fields = data.map(p => p.jsonkey);
        for(var i = 0; i < data.length; i++)
        { 
            obj[data[i].text] = data[i].jsonkey; 
        }
        console.log('Got XLS Fields As:: ' + JSON.stringify(obj));
        return(obj);  
    },

    exportToPDF: (payload, headers, reportHeading, logo, print = false) => {
        //export to pdf
        console.log('Export to PDF');
        //Get current time
        let date = utils.formatDate(new Date().toISOString().substr(0, 10));
        let time = utils.getTime();
        let base64Img = logo;
        // this.imgToBase64('@/assets/img/logo.png', function(base64) {
        //   base64Img = base64;
        // });
        // const head = [
        //   ["ID", "Card Number", "User Name", "First Name", "Last Name", "Enabled", "Reg. Date", "Reg. Time", "Exp. Date"],
        // ];
        // const columns = [
        //   { title: "ID", dataKey: "id", displayProperty: "id" },
        //   { title: "Card Number", dataKey: "attributes", displayProperty: "card_no" },
        //   { title: "User Name", dataKey: "attributes", displayProperty: "username" },
        //   { title: "First Name", dataKey: "attributes", displayProperty: "first_name" },
        //   { title: "Last Name", dataKey: "attributes", displayProperty: "last_name" },
        //   { title: "Enabled", dataKey: "attributes", displayProperty: "enabled" },
        //   { title: "Reg. Date", dataKey: "attributes", displayProperty: "reg_date" },
        //   { title: "Reg. Time", dataKey: "attributes", displayProperty: "reg_time" },
        //   { title: "Exp. Date", dataKey: "attributes", displayProperty: "exp_date" },
        // ];
        const columns = utils.getPDFColumns(headers);
        // Default export is a4 paper, portrait, using millimeters for units
        //const doc = new jsPDF();
        //const doc = new this.$jspdf();
        //const doc = new this.$jspdf({
        const doc = new jsPDF({
            orientation: "landscape",
            unit: "in",
            format: "letter"
        });
        let totalPagesExp = '{total_pages_count_string}';
        
        let pdfdata = utils.getPDFdata(payload, columns);
        // Using autoTable plugin
        doc.autoTable({
            // head: head,
            // columns: columns,
            // body: this.payload,
            // margin: { left: 0.5, top: 0.8 },
            // //theme: 'grid',
            // didParseCell: (data) => {
            //   const col = data.column.raw;
            //   const raw = data.cell.raw;
            //   if (col.displayProperty && col.dataKey) 
            //   {
            //     if(col.dataKey.trim().toLowerCase() === 'attributes')
            //     {
            //       const prop = col.displayProperty.trim().toLowerCase();
            //       for (const key in raw) 
            //       {
            //         if (raw.hasOwnProperty(key)) 
            //         {
            //           //console.log(`${key}: ${raw[key]}`);
            //           //if(key === 'card_no')
            //           const lkey = key.trim().toLowerCase();
            //           if(lkey === prop)
            //           {
            //             data.cell.text = raw[lkey] ? raw[lkey] : '';
            //             break;
            //           }
            //         }
            //       }
            //     }
            //   }
            // },
            columns: columns,
            body: pdfdata,
            margin: { left: 0.5, top: 0.8 },
            didDrawPage: (data) => {
            // Header
            doc.setFontSize(20)
            doc.setTextColor(40)
            if (base64Img) {
                //doc.addImage(base64Img, 'JPEG', data.settings.margin.left, 15, 10, 10)
                doc.addImage(base64Img, 'PNG', 0.5, 0.2, 0.4, 0.4);
            }
            //doc.text(this.reportHeading, data.settings.margin.left + 15, 22);
            doc.setFontSize(16).text(reportHeading, 1.0, 0.5);

            // Footer
            let pgstr = 'Page ' + doc.internal.getNumberOfPages();
            // Total page number plugin only available in jspdf v1.0+
            if (typeof doc.putTotalPages === 'function') {
                pgstr = pgstr + ' of ' + totalPagesExp;
            }
            pgstr = pgstr + " Printed On: " + date + " " + time;
            doc.setFontSize(10)

            // jsPDF 1.4+ uses getWidth, <1.4 uses .width
            var pageSize = doc.internal.pageSize;
            var pageHeight = pageSize.height ? pageSize.height : pageSize.getHeight();
            //doc.text(str, data.settings.margin.left, pageHeight - 10);
            doc.text(pgstr, data.settings.margin.left, pageHeight - 0.5);
            },
        });

        // Total page number plugin only available in jspdf v1.0+
        if (typeof doc.putTotalPages === 'function') {
            doc.putTotalPages(totalPagesExp)
        }

        if(print)
        {
            //doc.autoPrint({variant: 'non-conform'});
            doc.autoPrint({variant: 'javascript'});
            let oHiddFrame = document.createElement("iframe");
            oHiddFrame.style.position = "fixed";
            oHiddFrame.style.visibility = "hidden";
            oHiddFrame.src = doc.output('bloburl');
            document.body.appendChild(oHiddFrame);
        }
        else
        {
            doc.save(`${reportHeading}.pdf`);
        }
    },

    getPDFColumns: (data) => {
        //get the pdf columns
        //let data = this.headers;
        let arr = [];
        // let fields = data.map(p => p.jsonkey);
        for(var i = 0; i < data.length; i++)
        { 
            arr.push({'title': data[i].text, 'dataKey': data[i].jsonkey}); 
        }
        console.log('Got PDF Columns As:: ' + JSON.stringify(arr));
        return(arr);
    },

    getPDFheader() {
        //get the pdf header
    },

    getPDFdata: (payload, headers) => {
        let arr = [];
        for(var i = 0; i < payload.length; i++)
        { 
            let obj = {};
            for(var m = 0; m < headers.length; m++)
            {
            const obkey = headers[m].dataKey;
            obj[headers[m].dataKey] = lodashGet(payload[i], obkey);
            } 
            arr.push(obj); 
        }
        console.log('Got PDF flattened Data As:: ' + JSON.stringify(arr));
        return(arr);
    },

    formatDate: (mydate) => {
        if (!mydate) return null;

        const [year, month, day] = mydate.split('-');
        return(`${day}/${month}/${year}`);
    },

    getDate: () => {
        let today = new Date().toISOString().substr(0, 10);
        return(utils.formatDate(today));
    },

    getTime: () => {
        let today = new Date();
        let hours = ('0'+today.getHours()).slice(-2);
        let minutes = ('0'+today.getMinutes()).slice(-2);
        let seconds = ('0'+today.getSeconds()).slice(-2);
        return(`${hours}:${minutes}:${seconds}`);
    },

    exportToExcel: (payload, headers, reportHeading) => {
        //export to xlsx
        console.log('Export to XLSX');
        const columns = utils.jsonFieldsXLSX(headers);

        //Get flattened data
        let inject = utils.getXLSXdata(payload, columns);
        console.log('Got XLSX flattened data As:: ' + JSON.stringify(inject));
        //We can now create the document
        const data = XLSX.utils.json_to_sheet(inject);
        const wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, data, 'report');
        XLSX.writeFile(wb,`${reportHeading}.xlsx`);
    },

    jsonFieldsXLSX: (data) => {
        //let data = this.headers;
        let arr = [];
        for(var i = 0; i < data.length; i++)
        { 
            arr.push({'label': data[i].text, 'value': data[i].jsonkey}); 
        }
        console.log('Got XLSX Fields As:: ' + JSON.stringify(arr));
        return(arr);
    },

    getXLSXdata: (payload, headers) => {
        let arr = [];
        for(var i = 0; i < payload.length; i++)
        { 
            let obj = {};
            for(var m = 0; m < headers.length; m++)
            {
            const obkey = headers[m].value;
            obj[headers[m].label] = lodashGet(payload[i], obkey);
            } 
            arr.push(obj); 
        }
        return(arr);
    },

    imgToBase64: (src, callback) => {
        var outputFormat = src.substr(-3) === 'png' ? 'image/png' : 'image/jpeg';
        var img = new Image();
        img.crossOrigin = 'Anonymous';
        img.onload = function() {
            var canvas = document.createElement('CANVAS');
            var ctx = canvas.getContext('2d');
            var dataURL;
            canvas.height = this.naturalHeight;
            canvas.width = this.naturalWidth;
            ctx.drawImage(this, 0, 0);
            dataURL = canvas.toDataURL(outputFormat);
            callback(dataURL);
        };
        img.src = src;
        if (img.complete || img.complete === undefined) {
            img.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
            img.src = src;
        }
    },

    exportToWord: (payload, headers, reportHeading) => {
        //export to word
        console.log('Export to Word');

        const doc = new Document({
            creator: "Multisys Ltd.",
            description: `${reportHeading}`,
            title: `${reportHeading}`,
            subject: "REPORT",
            keywords: "REPORT",
            lastModifiedBy: "Multisys Ltd.",
            revision: "6",
            // externalStyles: "",
            // styles: "",
            // numbering: "",
            // footnotes: `${reportHeading}`,
            // hyperlinks: "",
            background: {
            color: "C45911",
            },
        });

        const table = utils.createDOCXTable(payload, headers);

        doc.addSection({
            size: {
            orientation: PageOrientation.LANDSCAPE,
            },
            properties: {
            pageNumberStart: 1,
            pageNumberFormatType: PageNumberFormat.DECIMAL,
            },
            margins: {
                top: 100,
                right: 1000,
                bottom: 100,
                left: 1000,
            },
            headers: {
                default: new Header({
                    children: [
                    new Paragraph({
                        text: `${reportHeading}`,
                        heading: HeadingLevel.HEADING_2,
                        alignment: AlignmentType.CENTER,
                    }),
                    ],
                }),
            },
            footers: {
                default: new Footer({
                    children: [
                    new Paragraph({
                        children: [
                            new TextRun(`Created On: ${utils.getDate()} ${utils.getTime()}`),
                            new TextRun({
                                children: [" Page: ", PageNumber.CURRENT],
                            }),
                            new TextRun({
                                children: [" of ", PageNumber.TOTAL_PAGES],
                            }),
                        ],
                        }),
                    ],
                }),
            },
            children: [table],
            //children: [],
        });

        // Packer.toBuffer(doc).then((buffer) => {
        //     fs.writeFileSync("REPORT.docx", buffer);
        // });

        Packer.toBlob(doc).then((blob) => {
            // saveAs from FileSaver will download the file
            saveAs(blob, `${reportHeading}.docx`);
        });
    },

    createDOCXTable: (payload, headers) => {
        //creates the table for docx export
        let dataRows = [];
        //Add Header
        let rowprops = {children:[],};
        for(var m = 0; m < headers.length; m++)
        {
            const text = headers[m].text;
            let content = text ? text : '';
            const dim = new TextRun({
            text: content,
            bold: true,
            font: "Calibri",
            allCaps: false,
            color: "FFFFFF",
            })
            let para = new Paragraph({
            children: [dim],
            heading: HeadingLevel.HEADING_2,
            alignment: AlignmentType.CENTER,
            });
            let cell = new TableCell({
            children: [para],
            shading: {
                color: "1C9CC6",
                fill: "1C9CC6",
                val: ShadingType.SOLID,
            },
            });
            rowprops.children.push(cell);
        }
        let row = new TableRow(rowprops);
        dataRows.push(row);
        //Add Data
        for(var i = 0; i < payload.length; i++)
        {
            let rowprops = {children:[],};
            let data = payload[i];
            for(var m = 0; m < headers.length; m++)
            {
            const obkey = headers[m].jsonkey;
            let content = lodashGet(data, obkey) ? lodashGet(data, obkey) : '';
            let para = new Paragraph(content);
            let cell = new TableCell({
                children: [para],
            });
            rowprops.children.push(cell);
            }
            let row = new TableRow(rowprops);
            dataRows.push(row);
        }
        let ret = new Table(
            {
            rows: dataRows,
            width: {
                size: 14500,
                type: WidthType.DXA,
            },
            }
        );
        return(ret);
    },

 };

