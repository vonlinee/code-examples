
$(document).ready(function() {

    $("#importFileView").click(function() {
        var scmServer = $("#scmServer").val();
        var scmFilePath = $("#scmFilePath").val();
        var scmFileRevision = $("#scmFileRevision").val();
        $("#importFileView").attr('href', scmServer + scmFileRevision + "@" + scmFilePath);

    });

    //Class based selector 
    $(".href-select").click(function() {
        var propName = $(this).text();
        var propVal = $(this).attr('itemprop');
        $("#newProperty").attr('readonly', true);
        $("#newProperty").val(propName);
        $("#newValue").val(propVal);

        $("#savePropertyBtn").hide();
        $("#updatePropertyBtn").show();
    });

    //Id based selector
    $("#addPropertyBtn").click(function() {
        $("#newProperty").attr('readonly', false);
        $("#updatePropertyBtn").hide();
        $("#savePropertyBtn").show();
    });


}); 