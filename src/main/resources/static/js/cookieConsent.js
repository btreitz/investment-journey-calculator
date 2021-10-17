const privacyPolicyShown = document.getElementById("privacy-policy");

if (!privacyPolicyShown) {
    window.cookieconsent.initialise({
        "palette": {
            "popup": {
                "background": "#252e39"
            },
            "button": {
                "background": "#335188"
            }
        },
        "theme": "classic",
        "content": {
            "message": "This website uses cookies for advertising purposes.",
            "dismiss": "Got it!",
            "link": "Learn more",
            "href": "/privacy"
        }
    });
}
