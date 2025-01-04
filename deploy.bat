gcloud compute scp emissor-fiscal-0.1.jar muril@conector:/home/muril --project=erpj-gr


mvn clean install -Pproducao -DskipTests && rundll32.exe cmdext.dll,MessageBeepStub && pause && cls && mvn package -Pproducao -DskipTests && rundll32.exe cmdext.dll,MessageBeepStub && pause && cls && mvn appengine:deploy -Pproducao -DskipTests && rundll32.exe cmdext.dll,MessageBeepStub && pause