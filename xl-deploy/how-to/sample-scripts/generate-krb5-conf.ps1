$domain = [System.DirectoryServices.ActiveDirectory.Domain]::GetComputerDomain()

Write-Output "[libdefaults]"
Write-Output "    default_realm = $($domain.Name.ToUpper())"
Write-Output ""
Write-Output "[realms]"
Write-Output "    $($domain.Name.ToUpper()) = {"
foreach ($kdc in $domain.DomainControllers) {
  Write-Output "        kdc = $($kdc.Name)"
}
Write-Output "    }"
