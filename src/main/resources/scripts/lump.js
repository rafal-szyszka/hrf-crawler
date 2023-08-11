var taskSelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_zadanie');
for (var i = 0; i < taskSelect.options.length; i++) {
    if (taskSelect.options[i].innerHTML == "argTask") {
        taskSelect.options[i].selected = true;
        taskSelect.dispatchEvent(new Event('change'));
    }
}

var lumpSelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_rodzajRyczaltu');
for (var i = 0; i < lumpSelect.options.length; i++) {
    if (lumpSelect.options[i].innerHTML == "argLumpType") {
        lumpSelect.options[i].selected = true;
        lumpSelect.dispatchEvent(new Event('change'));
    }
}

if (argIfLump) {
    var categorySelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_kategoriaKosztowKwotaRyczaltowa');
    for (var i = 0; i < categorySelect.options.length; i++) {
        if (categorySelect.options[i].innerHTML == "argCategory") {
            categorySelect.options[i].selected = true;
            categorySelect.dispatchEvent(new Event('change'));
        }
    }

    var lumpNameSelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_nazwaRyczaltuDlaKwotyRyczaltowej');
    for (var i = 0; i < lumpNameSelect.options.length; i++) {
        if (lumpNameSelect.options[i].innerHTML == "argLumpName") {
            lumpNameSelect.options[i].selected = true;
            lumpNameSelect.dispatchEvent(new Event('change'));
        }
    }

    var pointerNameSelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_nazwaWskaznikaDlaKwotyRyczaltowej');
    for (var i = 0; i < pointerNameSelect.options.length; i++) {
        if (pointerNameSelect.options[i].innerHTML == "argPointerName") {
            pointerNameSelect.options[i].selected = true;
            pointerNameSelect.dispatchEvent(new Event('change'));
        }
    }
    var pointerValue = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_wartoscWskaznika_tbbc_amount');
    pointerValue.value = "argPointerValue";
    pointerValue.dispatchEvent(new Event('change'));

    var documentsSelect = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_nazwaDokumentuDlaKwotyRyczaltowej');
    for (var i = 0; i < documentsSelect.options.length; i++) {
        if (documentsSelect.options[i].innerHTML == "argDocuments") {
            documentsSelect.options[i].selected = true;
            documentsSelect.dispatchEvent(new Event('change'));
        }
    }
}

var qualifiedCost = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_wydatkiKwalifikowalne_tbbc_amount');
qualifiedCost.value = "argQualifiedCost";
qualifiedCost.dispatchEvent(new Event('change'));

var subsidy = document.getElementById('zakres_rzeczowo_finansowy_wydatkiRyczaltowe_argIdx_wnioskowaneDofinansowanie_tbbc_amount');
subsidy.value = "argSubsidy";
subsidy.dispatchEvent(new Event('change'));