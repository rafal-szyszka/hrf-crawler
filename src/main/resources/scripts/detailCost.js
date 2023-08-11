var task = document.getElementById("zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_zadanie");
var description = document.getElementById("zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_szczegoloweWydatki");
var catalog = document.getElementById("zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_katalogKosztow");
var qualified = document.getElementById("zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_wydatkiKwalifikowalne_tbbc_amount");
var details = document.getElementById("zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_sposobRozeznaniaRynku");

var unitNumber = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_liczbaJednostek_tbbc_amount');
var unitPrice = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_cenaJednostkowa_tbbc_amount');
var peopleAmount = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_liczbaOsob');
var dayNumber = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_liczbaDniDelegacji');
var nightsNumber = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_liczbaNoclegow');
var offer1 = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_daneOferty1');
var offer2 = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_daneOferty2');
var offer3 = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_daneOferty3');
var dateRecognize = document.getElementById('zakres_rzeczowo_finansowy_szczegoloweWydatkiRyczaltowe_argIdx_dataRozeznaniaRynku');

for (var i = 0; i < task.options.length; i++) {
    if (task.options[i].text == "argTask") {
        task.options[i].selected = true;
        task.dispatchEvent(new Event('change'));
    }
}

description.value = "argDescription";
description.dispatchEvent(new Event('change'));

for (var i = 0; i < catalog.options.length; i++) {
    if (catalog.options[i].text == "argCatalog") {
        catalog.options[i].selected = true;
        catalog.dispatchEvent(new Event('change'));
    }
}

qualified.value = "argQualifiedCost";
qualified.dispatchEvent(new Event('change'));

details.value = "argDetails";
details.dispatchEvent(new Event('change'));

unitNumber.value = "argUnitNumber";
unitNumber.dispatchEvent(new Event('change'));

unitPrice.value = "argUnitPrice";
unitPrice.dispatchEvent(new Event('change'));

peopleAmount.value = "argPeopleAmount";
peopleAmount.dispatchEvent(new Event('change'));

dayNumber.value = "argDaysNumber";
dayNumber.dispatchEvent(new Event('change'));

nightsNumber.value = "argNightsNumber";
nightsNumber.dispatchEvent(new Event('change'));

offer1.value = "argOffer1";
offer1.dispatchEvent(new Event('change'));

offer2.value = "argOffer2";
offer2.dispatchEvent(new Event('change'));

offer3.value = "argOffer3";
offer3.dispatchEvent(new Event('change'));

if (argRecognizaValidDate) {
    dateRecognize.value = "argDateRecognize";
    dateRecognize.dispatchEvent(new Event('change'));
}