package com.odix.fr.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
 
@Service
public class LocalStorageService {
	
	  //Emplacement Root sur le serveur
	  private static final Path rootLocation = Consts.rootLocation;
	
	  //Emplacement photo sur le serveur
	  private static final Path rootLocationPhoto = Consts.rootLocationPhoto;
	  
	  //Emplacement cvodix sur le serveur
	  private static final Path rootLocationCvOdix = Consts.rootLocationCvOdix;
	  
	  //Emplacement cvoriginal sur le serveur
	  private static final Path rootLocationCvOriginal = Consts.rootLocationCvOriginal;
	  
	  //Emplacement fichier sur le serveur
	  private static final Path rootLocationFichierRappel = Consts.rootLocationFichierRappel;
	  
	  
	  //Fonction qui crypte le nom de la photo et l'insére sur le disque
	  public String addPhoto(MultipartFile file1) {
		  
			String message = "";
			String filename1 =UUID.randomUUID().toString()+getExtensionByStringHandling(file1.getOriginalFilename());
			
		    try 
		    {
		      this.savePhoto(file1, filename1);
		      message = LocalStorageService.rootLocationPhoto.toString().replace("\"", "")+"/"+filename1;
		      return message;
		      
		    } 
		    catch (Exception e) 
		    {
		      return null;
		    }
		}
	  
	  //Fonction qui crypte le nom du CvOdix et l'insére sur le disque
	  public String addCvOdix(MultipartFile file2) {
		  
			String message = "";
			String filename2 =UUID.randomUUID().toString()+getExtensionByStringHandling(file2.getOriginalFilename());
			
		    try 
		    {
		      this.saveCvOdix(file2, filename2);
		      message = LocalStorageService.rootLocationCvOdix.toString().replace("\"", "")+"/"+filename2;
		      return message;
		      
		    } 
		    catch (Exception e) 
		    {
		      return null;
		    }
		}
	  
	  //Fonction qui crypte le nom du CvOriginal et l'insére sur le disque
	  public String addCvOriginal(MultipartFile file3) {
		  
			String message = "";
			String filename3 =UUID.randomUUID().toString()+getExtensionByStringHandling(file3.getOriginalFilename());
			
		    try 
		    {
		      this.saveCvOriginal(file3, filename3);
		      message = LocalStorageService.rootLocationCvOriginal.toString().replace("\"", "")+"/"+filename3;
		      return message;
		      
		    } 
		    catch (Exception e) 
		    {
		      return null;
		    }
		}
	 
	  //Fonction qui crypte le nom du fichier et l'insére sur le disque
	  public ArrayList<String> addFichierRappel(MultipartFile file) {
		  
			String urlFichier = "";
			String nomFichier = "";
			
			String filenameOriginal =file.getOriginalFilename();
			String filenameModified =UUID.randomUUID().toString()+getExtensionByStringHandling(file.getOriginalFilename());
			
		    try 
		    {
		      this.saveFichierRappel(file, filenameModified);
		      
		      urlFichier = LocalStorageService.rootLocationFichierRappel.toString().replace("\"", "")+"/"+filenameModified;
		      nomFichier = filenameOriginal;
		      
		      ArrayList<String> urls = new ArrayList<String>();
		      
		      urls.add(urlFichier);
		      urls.add(nomFichier);
		      
		      return urls;
		      
		    } 
		    catch (Exception e) 
		    {
		      return new ArrayList<String>();
		    }
		}
	  
	  //save Photo
	  public void savePhoto(MultipartFile file1, String filename1 ) {
	    try 
	    {
	      Files.copy(file1.getInputStream(), LocalStorageService.rootLocationPhoto.resolve(filename1));
	    } 
	    catch (Exception e) 
	    {
	      throw new RuntimeException("Erreur de sauvegarde de la Photo - saveFile !");
	    }
	  }
	  
	  //save CvOdix
	  public void saveCvOdix(MultipartFile file2, String filename2 ) {
		    try 
		    {
		      Files.copy(file2.getInputStream(), LocalStorageService.rootLocationCvOdix.resolve(filename2));
		    } 
		    catch (Exception e) 
		    {
		      throw new RuntimeException("Erreur de sauvegarde du CvOdix - saveFile !");
		    }
		  }
	  
	  //save CvOriginal
	  public void saveCvOriginal(MultipartFile file3, String filename3 ) {
		    try 
		    {
		      Files.copy(file3.getInputStream(), LocalStorageService.rootLocationCvOriginal.resolve(filename3));
		    } 
		    catch (Exception e) 
		    {
		      throw new RuntimeException("Erreur de sauvegarde du CvOriginal - saveFile !");
		    }
		  }
	  
	  //save Fichier
	  public void saveFichierRappel(MultipartFile file, String filename ) {
	    try 
	    {
	      Files.copy(file.getInputStream(), LocalStorageService.rootLocationFichierRappel.resolve(filename));
	    } 
	    catch (Exception e) 
	    {
	      throw new RuntimeException("Erreur de sauvegarde du Fichier Rappel - saveFile !");
	    }
	  }
	  
	  //Supprime une seule photo via son Path
	  public void deletePhoto(String filename) {

		  File rootLocation = new File(filename);
		  try 
		  {
			  rootLocation.delete();
		  } 
		  catch (Exception e) 
		  {
			throw new RuntimeException("Erreur de suppression de l'image !");
		  }
		}
	  
	  //Supprime un CvOdix via son Path
	  public void deleteCvOdix(String filename) {

		  File rootLocation = new File(filename);
		  try 
		  {
			  rootLocation.delete();
		  } 
		  catch (Exception e) 
		  {
			throw new RuntimeException("Erreur de suppression du CvOdix !");
		  }
		}
	 
	  //Supprime un CvOriginal via son Path
	  public void deleteCvOriginal(String filename) {

		  File rootLocation = new File(filename);
		  try 
		  {
			  rootLocation.delete();
		  } 
		  catch (Exception e) 
		  {
			throw new RuntimeException("Erreur de suppression du CvOriginal !");
		  }
		}
	  
	  //Supprime un seule fichier via son Path
	  public void deleteFichier(String filename) {

		  File rootLocation = new File(filename);
		  try 
		  {
			  rootLocation.delete();
		  } 
		  catch (Exception e) 
		  {
			throw new RuntimeException("Erreur de suppression du fichier !");
		  }
		}

	  //Retourne l'extention d'un fichier : image ou autre
	  public String getExtensionByStringHandling(String filename) 
	  {
		    return "."+filename.substring(filename.lastIndexOf('.') + 1);
	  }
	  
	  //Liste tous les fichiers dans le dossier Root : le dossier qui contient tous les Uploads
	  public String[] listAllFilesOfRootDirectory() {
		  
		  String[] list = null;
		  try 
		    {
			  // Create a file object 
			  File fileObject = new File(LocalStorageService.rootLocation.toString()); 
			  
			  // Get all the names of the files present in the given directory 
	          list = fileObject.list(); 
		    } 
		    catch (Exception e) 
		    {
		      throw new RuntimeException("Erreur de listing des fichiers dans Root !");
		    }
		  return list;
	  }
	}