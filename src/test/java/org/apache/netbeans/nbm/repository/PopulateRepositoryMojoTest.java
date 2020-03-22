package org.apache.netbeans.nbm.repository;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import junit.framework.TestCase;
import org.apache.maven.model.Dependency;

/**
 * 
 * @author Milos Kleint
 */
public class PopulateRepositoryMojoTest extends TestCase /** AbstractMojoTestCase  the only way out of dependency hell.**/{
    
    public void testStripClusterName()
    {
        assertEquals( "platform", PopulateRepositoryMojo.stripClusterName( "platform9" ) );
        assertEquals( "platform", PopulateRepositoryMojo.stripClusterName( "platform11" ) );
        assertEquals( "nb", PopulateRepositoryMojo.stripClusterName( "nb6.9" ) );
        assertEquals( "extra", PopulateRepositoryMojo.stripClusterName( "extra" ) );
    }

    public void testInstall() throws Exception
    {
//TODO how is this done on maven3?
//        PopulateRepositoryMojo mojo = ( PopulateRepositoryMojo ) lookupMojo( "populate", new File( getBasedir(), "src/test/resources/PopulateMojoTest.xml" ) );
//        File repo = new File( System.getProperty( "java.io.tmpdir" ), "PopulateRepositoryMojoTest" );
//        FileUtils.deleteDirectory( repo );
//        mojo.localRepository = new DefaultArtifactRepository( "test", repo.toURI().toString(), new DefaultRepositoryLayout() );
//        Artifact art1 = mojo.createArtifact( "testarg", "1.0", "testgrp" );
//        File f = File.createTempFile( "PopulateRepositoryMojoTest", ".nbm" );
//        f.deleteOnExit();
//        Artifact art2 = mojo.createAttachedArtifact( art1, f, "nbm-file", null );
//        assertEquals( "nbm", art2.getArtifactHandler().getExtension() );
//        //TODO does not work because of missing session
////        mojo.install( f, art2 );
////        assertTrue( new File( repo, "testgrp/testarg/1.0/testarg-1.0.nbm" ).isFile() );
////        assertFalse( new File( repo, "testgrp/testarg/1.0/testarg-1.0.jar" ).isFile() );
    }
    
    public void testSplit() throws Exception
    {
        Dependency dep1 = PopulateRepositoryMojo.splitDependencyString("org.apache.maven:apache-maven:3.6.3:bin@zip");
        assertEquals("org.apache.maven", dep1.getGroupId() );
        assertEquals("apache-maven", dep1.getArtifactId());
        assertEquals("3.6.3", dep1.getVersion());
        assertEquals("bin", dep1.getClassifier());
        assertEquals("zip", dep1.getType());
        
        Dependency dep2 = PopulateRepositoryMojo.splitDependencyString("org.apache.maven:apache-maven:3.6.3:myclassifier");
        assertEquals("org.apache.maven", dep2.getGroupId() );
        assertEquals("apache-maven", dep2.getArtifactId());
        assertEquals("3.6.3", dep2.getVersion());
        assertEquals("myclassifier", dep2.getClassifier());
        assertEquals("jar", dep2.getType());
        
        Dependency dep3 = PopulateRepositoryMojo.splitDependencyString("org.apache.maven:apache-maven:3.6.3");
        assertEquals("org.apache.maven", dep3.getGroupId() );
        assertEquals("apache-maven", dep3.getArtifactId());
        assertEquals("3.6.3", dep3.getVersion());
        assertEquals("", dep3.getClassifier());
        assertEquals("jar", dep3.getType());
    }

    public void testEncode() throws Exception
    {
        assertEquals( "057558504e1d03e57ce6fd80ad983b3c2e803b40", PopulateRepositoryMojo.encode( new byte[] {
            (byte) 0x05, (byte) 0x75, (byte) 0x58, (byte) 0x50, (byte) 0x4e, (byte) 0x1d, (byte) 0x03, (byte) 0xe5, (byte) 0x7c, (byte) 0xe6,
            (byte) 0xfd, (byte) 0x80, (byte) 0xad, (byte) 0x98, (byte) 0x3b, (byte) 0x3c, (byte) 0x2e, (byte) 0x80, (byte) 0x3b, (byte) 0x40,
        } ) );
        assertEquals( "ca70822c47a67fc3a11670270567c2d01566dae1", PopulateRepositoryMojo.encode( new byte[] {
            (byte) 0xca, (byte) 0x70, (byte) 0x82, (byte) 0x2c, (byte) 0x47, (byte) 0xa6, (byte) 0x7f, (byte) 0xc3, (byte) 0xa1, (byte) 0x16,
            (byte) 0x70, (byte) 0x27, (byte) 0x05, (byte) 0x67, (byte) 0xc2, (byte) 0xd0, (byte) 0x15, (byte) 0x66, (byte) 0xda, (byte) 0xe1,
        } ) );
    }

}
