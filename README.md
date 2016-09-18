# HierachicalLayoutFromRecursiveJson
A sample example to parse recursive json rest api data and populate data in recursive tree layout 

Parent hierarchy layout for recursive json data below
{
    "orgList": [
        {
            "orgInfo": {
                "id": 1,
                "orgName": "Organization X"
            },
            "childOrgs": [
                {
                    "orgInfo": {
                        "id": 2,
                        "orgName": "Organization X.1"
                    },
                    "childOrgs": [
                        {
                            "orgInfo": {
                                "id": 3,
                                "orgName": "Organization X.1.1"
                            },
                            "childOrgs": []
                        }
                    ]
                },
                {
                    "orgInfo": {
                        "id": 4,
                        "orgName": "Organization X.2"
                    },
                    "childOrgs": []
                }
            ]
        },
        {
            "orgInfo": {
                "id": 5,
                "orgName": "Organization Y"
            },
            "childOrgs": []
        }
    ]
}

![dashboardlevel1](https://cloud.githubusercontent.com/assets/13640630/18614053/5c4b1502-7da4-11e6-922b-d9b8519379f1.jpeg)

Child layout for json data below combined with parent
{
    "siteList": [
        {
            "id": 1,
            "siteName": "Site A",
            "organizationId": 1
        },
        {
            "id": 2,
            "siteName": "Site B",
            "organizationId": 1
        }
    ]
}

![dashboardlevel2](https://cloud.githubusercontent.com/assets/13640630/18614054/5f28e51a-7da4-11e6-8e0d-516c3d27e9e3.jpeg)
