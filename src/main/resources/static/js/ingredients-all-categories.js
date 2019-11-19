$(document).ready(function () {
    $("input[name$='options']").click(function () {
        let radio_value = $(this).val();

        $("#category").text('All ' + radio_value).show();

        if (radio_value === 'Meats') {
            $(".allMeats").show();
            $(".allCheeses").hide();
            $(".allVegetables").hide();
        } else if (radio_value === 'Cheeses') {
            $(".allMeats").hide();
            $(".allCheeses").show();
            $(".allVegetables").hide();
        } else if (radio_value === 'Vegetables') {
            $(".allMeats").hide();
            $(".allCheeses").hide();
            $(".allVegetables").show();
        }
    });
    $('[name="options"]:checked').trigger('click');
});
