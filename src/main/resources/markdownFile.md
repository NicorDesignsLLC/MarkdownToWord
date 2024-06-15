**What’s Changing**

**CHG0033853**

**TFS14946**  
PROD APO's / FPO's sending wrong billing method to PKMS for accounts with 3rd party billing setup.  
**INC0090582**  
TFS17020 IT Steering Committee Request - Always Print Packslips - Release to PkMS

---

**What is changing?**  
ESB change to fix the bugs related to PKMS & Billing.

**ESB:**

| Repository Branch | Jar File(s) to be changed | Singletons | Server/Karaf | Notes |
|-------------------|---------------------------|------------|--------------|-------|
| master-baseline   | common-3.1.6.jar, common-esb-ws-3.1.0.jar, publishSORelease2PKMS-3.0.8.jar | filerouter | | |
|                   |                           | docSaver   | | |
|                   |                           | ezpoCSO    | | |

**Active Servers**

| Active Servers     | Group         | Changed server -> karaf | Bundle(s) being changed in the Karaf                                | Notes |
|--------------------|---------------|-------------------------|---------------------------------------------------------------------|-------|
| prd-esbv10-01a     | PKMS          |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-01b     | OMS           |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-02a     | ORDER PUBLISH |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           | Here is where the singletons must be running. (listed above) |
| prd-esbv10-02a     | BACKORDER OMS |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-02b     | ORDER PUBLISH |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           | This is not running here |
| prd-esbv10-02b     | ORDER PUBLISH |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-03b     | WEB           |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-03c     | WEB           |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-04b     | SOURCING      |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-04b     | SOURCING      |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-04b     | SOURCING      |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-05b     | SOURCING      |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-05b     | SOURCING      |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-05b     | SOURCING      |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-06b     | SOURCING      |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-06b     | SOURCING      |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-06b     | SOURCING      |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-07a     | SOURCING      |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-08b     | SOURCING      |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-08b     | SOURCING      |                         | common-3.1.6.jar, common-esb-ws-3.1.0.jar                           |       |
| prd-esbv10-02a     | ORDER PUBLISH |                         | publishSORelease2PKMS-3.0.8.jar                                     |       |
| prd-esbv10-02b     | ORDER PUBLISH |                         | publishSORelease2PKMS-3.0.8.jar                                     |       |

---

**When?**  
06-17-2024 7am

---

**System Changing**  
**Change Type Non-Web?**
- [x] ESB
- [ ] Sterling
- [ ] Queue
- [ ] DB Changes
- [ ] Scripting
- [x] Configurations

**Change Type Web?**
- [ ] MyOMS v2
- [ ] SPR Advantage v3
- [ ] Classic
- [ ] DB Changes
- [ ] Menu changes

---

**Deployment Steps and Details (if Sunday we do Stop/Start of the karafs too)**

**For ESB:**
1. Backup the target bundle(s) and uninstall them from the karaf(s) one at a time if more.
2. If sourcing karaf, stop the camel route creationSourcingRoute EDI2ESBSOReserveQ.
3. Make sure the logs stop logging order data.
4. Uninstall the affected bundle(s) (Stop karaf if Sunday).
5. Remove the files from the deploy directory and remove the cache for the particular bundle from the data/cache location if the bundle was not uninstalled.
6. Add the needed jars to the Deploy directory (Start karaf if Sunday).
7. Verify the file(s) have a new version, add it in the target Karaf in its deploy directory.
8. Verify the bundle is being picked up and updated.
9. If sourcing karaf, start the camel route creationSourcingRoute EDI2ESBSOReserveQ.
10. Check the logs and the cache dir.

---

**Verification Test After Deployment**  
TBD.

---

**Impact**  
No impact.

---

**Roll back Plan**

**ESB:**
1. If sourcing karaf, stop the camel route creationSourcingRoute EDI2ESBSOReserveQ.
2. Make sure the logs stop logging order data.
3. Uninstall the affected bundle(s) (Stop karaf if Sunday).
4. Remove the files from the deploy directory and remove the cache for the particular bundle from the data/cache location if the bundle was not uninstalled.
5. Add the needed jars to the Deploy directory (Start karaf if Sunday).
6. Verify the file(s) have a new version, add it in the target Karaf in its deploy directory.
7. Verify the bundle is being picked up and updated.
8. If sourcing karaf, start the camel route creationSourcingRoute EDI2ESBSOReserveQ.
9. Check the logs and the cache dir.
