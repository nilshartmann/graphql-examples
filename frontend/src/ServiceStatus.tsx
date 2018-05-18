import * as React from "react";
interface JavaServiceStatus {
  name: string;
  javaVersion: string;
  uptime: string;
}

interface NodeJsServiceStatus {
  name: string;
  nodeJsVersion: string;
  uptime: string;
}

interface ServiceStatusProps {
  status: JavaServiceStatus | NodeJsServiceStatus;
}

const ServiceStatus = ({ status }: ServiceStatusProps) => {
  const isJavaService = (candidate: any): candidate is JavaServiceStatus => !!candidate.javaVersion;

  return isJavaService(status) ? (
    <div>
      {status.name} running on Java version {status.javaVersion} since {status.uptime}
    </div>
  ) : (
    <div>
      {status.name} running on NodeJS version {status.nodeJsVersion} since {status.uptime}
    </div>
  );
};

export default ServiceStatus;
