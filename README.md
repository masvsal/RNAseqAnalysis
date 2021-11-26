##Personal Project

###Samuel Salitra - 10/1/21

Questions to Answer:
- **What will the Application Do?** </br>
It will be a file system that a biologist might use to keep track of their experiments and data files. Additionally, it will have the functionality to perform a simple analysis of any *RNAseq* data file in its file system. A short summary of the RNAseq technique is provided below. 
- **Who will use it?** </br>
Bioinformaticians might use it in their day-to-day work to keep track of their work and automate the tedious task of analyzing RNA sequence data.
- **Why is this project of interest to you?** </br>
I would like to work in bioinformatics so I want to understand the basic workflow of a typical bioinformatic program.

####User Stories - Phase 1 

- As a user, I want to add be able to add or remove an experiment to the directory of my experiments
- As a user, I want to be able to change the description of an experiment
- As a user, I want to be able to add or remove a data file to an experiment
- As a user, I want to be able to choose a significance threshold and find significant differences between the gene expression of a control sample and a challenge sample within an RNAseq data file.

####User Stories - Phase 2

- As a user, I want to be able to save my current experiment directory to file.
- As a user, I want to have the option to load a saved experiment directory from file when I start my application.

####User Stories - Phase 3
- As a user, I want to be able to add multiple experiments or data files to experiment and data file directories
- As a user I want to be able to remove multiple experiments or data files from the appropriate directories
- As a user, I want to be able to see a graph of the fold change across all genes in an RNAseq file.

####Phase 4 - Task 3
- My project can be described as a collection of different groups of classes that were designed for different phases of the project throughout the term. Each collection has better coupling between classes within each collection than between collections. Additionally, the data structures in my project were designed before I head learned about the composite and observer design patterns. If I had known about these patterns beforehand, the coupling both within and between the model and ui packages would be improved.  

- Given further time, I would implement the following changes/refactoring into my project:
- UI package:
   - reduce references to experiment directory, experiment and data file classes by storing each field once in the main interface class and "getting" this field in all other classes
   - implement an observer - observable design pattern between the experiment directory/data file directory classes and the data file directory/data file display classes. 
   - instead of storing each in an individual field, store tool objects as a list field in toolbar.
- model package:
  - implement a composite pattern to store data files. named file = leaf, directory = composite. This would reduce redundancies in methods between classes and would make return types easier to deal with.

### Citations:
- code in persistence package modelled off of CPSC210 JSONSerializationDemo:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

####TODO (For Personal Use)

- add the following user interaction: Add/remove experiments (DONE), add/remove data files (DONE), 
add description to experiments and data files (DONE)
- problem: when I add an experiment, go into the experiment and quit out to experiment directory, 
the experiment is gone. (FIXED, removed extra initialization)
- reduce  code duplication in add/remove data files/experiments (partway done)
- add new data file-level selection screen (DONE)
- abstract qPCR and RNAseq data (what are the fields, what operations to do to them)
- add independent display data file method (DONE)
- assign marker gene that can be used for marker comparison
- search for above threshold
- google fpKm
- use swing file chooser to add ability to add new csv files


