timestamps {
try {
node('4BitDev2-wvm') {
    
    date = new Date()
    env.today =  date.format("MMdd")
	env.release = '2021.1.00'
    
    stage('Checkout'){
        
        dir('C:/jenkins/workspace')
        {
            git 'https://github.com/pratik-shah2611/innovateIn48.git'
            
        }
        
    }
    stage('Build'){
        
        dir('C:/jenkins/workspace') {
        powershell '''	
            Compress-Archive -Path C:/jenkins/workspace/studentsDemo/* -DestinationPath studentsDemo.$env:release.$env:today-$env:BUILD_NUMBER.zip
            exit $LASTEXITCODE
	    '''
	    // sshPublisher(publishers: [sshPublisherDesc(configName: 'masterJenkins', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: "studentsDemo.${release}.${today}-${BUILD_NUMBER}.zip")], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
	    stash includes: "studentsDemo.${release}.${today}-${BUILD_NUMBER}.zip", name: 'myApp'
        }
    }
    stage('StartAzVM'){
        powershell '''
         Start-AzVM -ResourceGroupName "4Bit" -Name "4BitDev-wvm"
         Start-Sleep -s 60
         '''
    }
    
}

node('4BitDev-wvm') {

    stage('Deploy'){
        
        powershell '''
            Remove-Item -Path C:/apps -Force -Recurse
            Remove-Item -Path C:/testAutomation/test-output -Force -Recurse
            New-Item -ItemType "directory" -Path C:/apps
            New-Item -ItemType "directory" -Path C:/apps/studentsDemo
            '''
        
        dir('C:/apps') {
            
        unstash 'myApp'
        
        powershell '''
            Expand-Archive -LiteralPath "studentsDemo.$env:release.$env:today-$env:BUILD_NUMBER.zip" -DestinationPath C:/apps/studentsDemo
        '''
        }
        
        bat '''
            CALL C:\\jenkins\\nssm-2.24\\win64\\nssm.exe install studentApp python C:\\apps\\studentsDemo\\student.py
            CALL C:\\jenkins\\nssm-2.24\\win64\\nssm.exe start studentApp
            '''
        
    }
    stage('Test'){
        
        bat '''
        CALL cd C:/testAutomation
        CALL java -jar C:/testAutomation/student_app_test_case.jar
        '''
    }
    stage('PublishReports'){
        
        publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'C:\\testAutomation\\test-output', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
    }
    
}



} finally {
    
    node('4BitDev-wvm') {
        bat '''
            CALL C:\\jenkins\\nssm-2.24\\win64\\nssm.exe stop studentApp
            CALL C:\\jenkins\\nssm-2.24\\win64\\nssm.exe remove studentApp confirm
            
            '''
        
    }
    node('4BitDev2-wvm') {
    
    stage('StopAzVM'){
        
        powershell '''
         Stop-AzVM -ResourceGroupName "4Bit" -Name "4BitDev-wvm" -Force
         '''
    }
    
    
           
        
    
    
}
    
}

    
}