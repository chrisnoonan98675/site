---
title: Configure custom dashboards
categories:
- xl-release
subject:
- Dashboards
tags:
- reports
- custom dashboards
- dashboards
- metrics
- release value stream
---

In XL Release 8.1 and later you can configure customized global dashboards. Custom dashboards can be used to monitor a range of metrics across multiple releases, various folders and templates, Jenkins builds, JIRA tickets, or any custom item you wish to monitor.

## Permissions and roles
The Create dashboard global permission is required to create dashboards, see [Grant the Create dashboard permission](#grant-the-create-dashboard-permission). Users with this permission can create and edit dashboards.

The edit dashboard "Editors" role is required to modify dashboards. Users with this role can view and edit dashboards but not create them. This role is set by the dashboard owner when configuring a dashboard, see [Create a dashboard](#create-a-dashboard).

The view dashboard "Viewers" role is view only. Users with this role can cannot modify or create a dashboard. This role is set by the dashboard owner when configuring a dashboard, see [Create a dashboard](#create-a-dashboard).

### Grant the Create dashboard permission
To allow users or user roles to create a dashboard:
1. Click **User management** and then click the **Permissions** tab.
2. Under the **Roles** section, add the user or user group name beside the **Create dashboard** action.

## Dashboards

### Create a dashboard
To create a dashboard:
1. In the top bar, click **Dashboards**.
2. On the right of the screen, click **Add dashboard**.
3. In the **Name** field, add a unique name for the dashboard.
4. In the **Columns** field, define the number of columns that will be used in the dashboard grid.
5. In the **Dashboard owner** field, define which user or user group owns the dashboard.    
 **Note:** A dashboard owner has modify and delete permissions. A dashboard can be assigned to one owner only.
6. In the **Viewers** field, define which users or user roles can view the dashboard.
7. In the **Editors** field, define which users or user roles can edit the dashboard.
8. Click **Save**.

### Delete a dashboard
To delete a dashboard:
1. Select the dashboard you wish to delete.
1. On the top right of the screen, click **Configure dashboard**.
2. Click **Delete dashboard**.    
**Note:** Only the dashboard owner or a user with the create dashboard permission can delete a dashboard.

### Edit a dashboard
To edit a dashboard:
1. Select the dashboard you wish to edit.
1. On the top right of the screen, click **Configure dashboard**.
2. Click **Edit properties**.
3. Edit the dashboard properties.
4. Click **Save**.

### Add a tile to a dashboard
To add a tile to a dashboard:
1. Select a dashboard.
2. Click **Configure dashboard**.
3. Click **Add tiles**.
4. Choose a tile and click **Add**.    
**Note:** See tiles description for a detailed description of all available tiles.

### Configure a tile
To configure a tile:
1. Select a dashboard.
1. Click **Configure dashboard**.
1. Hover over a tile.
1. Click ![Gear icon](/images/button_configure_tile.png).
1. Edit the tile properties.
1. Click **Save**.

### Add a filter to a tile
Filters can be applied to certain tiles, these are referred to as reporting tiles.

Filter types are:
- **Parent folder:** Filter release data based on a specific parent folder name.
- **Source templates:** Filter release data based on specific source template names.
- **Release tags:** Filter release data based on specific release tags.

To add a filter to a tile:
1. Select a dashboard.
1. Click **Configure dashboard**.
1. Hover over a tile.
1. Click ![Gear icon](/images/button_configure_tile.png).
1. Edit the tile properties.
1. Click **+ Add filter**.    
<br/>
    *To filter data based on specific parent folder names:*    
        - Click **Select filter** and select **Parent folder**.    
        - In the blank field under **matches**, enter parent folder names.    
        **Note:** This filter returns data from a direct parent only. If you want to return data that is from more than one parent folder, add all folder names.   
        <br/>
    *To filter data based on specific source template names:*    
        - Click **Select filter** and select **Source template**.    
        - In the blank field under **matches**, enter source template names.    
        <br/>
    *To filter databased on specific release tags:*    
        - Click **Select filter** and select **Release tags**.    
        - In the field under **contains all**, select a logical operator.    
        <br/>
     Two logical operators are available on the release tags filter:        
        - Select **contains all** to return release data that has all of the specified tags. If data items do not have all of the specified tags no data will be returned.     
        *Or*     
        - Click **contains any** to return release data that has any of the mentioned tags.
        - In the blank field under the logical operator, enter tag names.  
        <br/>
1. Click **Save**.  

### Move a tile
To move a tile:
1. Select a dashboard.
1. Click **Configure dashboard**.
1. Hover over a tile and click ![Move icon](../images/button-move-tile.png).
1. Drag the tile to a new location on the grid.

### Delete a tile
To delete a tile:
1. Select a dashboard.
1. Click **Configure dashboard**.
1. Hover over a tile and click ![Delete icon](/images/button-delete-tile2.png).

### Resize a tile and customize the dashboard layout
Tiles can span multiple columns and roles. This makes it easier to view metrics on large data sets.

To resize a title or customize the layout:
1. Hover over the left, right, or bottom edge of any tile. A two sided arrow will appear.
2. Click and drag the tile until it covers the desired area.
