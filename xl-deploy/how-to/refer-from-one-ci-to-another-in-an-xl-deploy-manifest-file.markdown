---
title: Refer from one CI to another in an XL Deploy manifest file
subject:
- Packaging
categories:
- xl-deploy
tags:
- package
- manifest
- ci
---


You can refer from one configuration item (CI) to another in an XL Deploy deployment package manifest file as follows:

    <sample.Sample name="referencing">
        <ciReferenceProperty ref="AnimalZooBE" />
        <ciSetReferenceProperty>
            <ci ref="AnimalZooBE" />
        </ciSetReferenceProperty>
        <ciListReferenceProperty>
            <ci ref="AnimalZooBE" />
        </ciListReferenceProperty>
    </sample.Sample>
